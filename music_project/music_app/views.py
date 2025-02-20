import os
from django.core.files import File
from django.utils import timezone
from django.http import HttpResponse
from mutagen.mp3 import MP3
from mutagen.wave import WAVE
from django.core.files.storage import default_storage
from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login , logout
from django.contrib.auth.models import User , Group
from .forms import Genre_Form , Artist_Form , Album_Form , Track_Form , Cut_Track_Form
from .models import Genre , Album , Track, Artist
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from .utils import cut_mp3_with_mutagen

def genre_list_view(request):
    genre_list=Genre.objects.all()
    return render(request, 'genre_list.html', {'genre_list': genre_list})

@login_required(login_url='/login/')
def add_genre_view(request):
    if request.method == 'POST':
        form = Genre_Form(request.POST)
        if form.is_valid():
            genre_name = form.cleaned_data['name']
            genre, created = Genre.objects.get_or_create(name=genre_name)

            if created:
                messages.success(request, f'Жанр "{genre_name}" успешно добавлен!')
            else:
                messages.info(request, f'Жанр "{genre_name}" уже существует.')

            return redirect('genre_list')
    else:
        form = Genre_Form()

    return render(request, 'add_genre.html', {'form': form})

@login_required(login_url='/login/')
def add_artist_view(request):
    if request.method=='GET':
        form=Artist_Form()
        return render(request, template_name='artist_create.html',context={'form':form })
    if request.method=='POST':
        form=Artist_Form(request.POST, request.FILES, user=request.user)
        if form.is_valid():
            form.save()

            artist_group, _ = Group.objects.get_or_create(name="Artist")
            request.user.groups.clear()
            request.user.groups.add(artist_group)
            request.user.save()

            messages.success(request, 'Вы успешно сменили профиль на артиста')
            return redirect('home')
    else:
        form = Artist_Form()
    return render(request, template_name='artist_create.html',context={'form':form })

@login_required(login_url='/login/')   
def artist_view (request, artist_id):
    if request.method=='GET':
        current_artist=Artist.objects.get(id=artist_id)
        tracks=Track.objects.filter(artist=current_artist)
        return render(request, template_name='artist.html',context={'tracks':tracks})

@login_required(login_url='/login/')  
def create_album_view(request):
    if request.method == 'POST':
        form = Album_Form(request.POST, request.FILES)
        if form.is_valid():
            form.save()
            messages.success(request, 'Альбом успешно добавлен!')
            return redirect('album_list')
    else:
        form = Album_Form()

    return render(request, 'album_create.html', {'form': form})

def album_view(request):
    if request.method == 'GET':
        albums=Album.objects.all()
        return render(request, 'album_list.html', {'albums': albums})
    
@login_required(login_url='/login/')  
def add_track_view(request):
    if request.method == 'POST':
        form = Track_Form(request.POST, request.FILES)
        if form.is_valid():
            track = form.save(commit=False)

            track.plays = 0

            track_image = request.FILES.get('track_image')
            if track_image:
                image_path = default_storage.save(f'music/track_image/{track_image.name}', track_image)
                track.track_image = image_path

            audio_file = request.FILES.get('audio_file')
            temp_path = default_storage.save(f'temp/{audio_file.name}', audio_file)

            try:
                file_path = default_storage.path(temp_path)
                if audio_file.name.endswith('.mp3'):
                    audio = MP3(file_path)
                elif audio_file.name.endswith('.wav'):
                    audio = WAVE(file_path)
                else:
                    messages.error(request, 'Поддерживаются только форматы MP3 и WAV.')
                    default_storage.delete(temp_path)
                    return redirect('add_track')

                total_seconds = int(audio.info.length)
                track.duration = total_seconds

            except Exception as e:
                messages.error(request, f'Ошибка при обработке аудиофайла: {e}')
                default_storage.delete(temp_path)
                return redirect('add_track')

            default_storage.delete(temp_path)
            track.save()

            messages.success(request, f'Трек "{track.title}" успешно добавлен с длительностью {track.duration}.')
            return redirect('track_detail', track_id=track.id)
        else:
            messages.error(request, 'Исправьте ошибки в форме.')
    else:
        form = Track_Form()

    return render(request, 'add_track.html', {'form': form})


def tracks_view(request):
    if request.method == 'GET':
        tracks=Track.objects.all()
        return render(request, 'track_list.html', {'tracks': tracks})

def track_detail_view(request, track_id):
    if request.method == 'GET':
        current_track=Track.objects.get(id=track_id)
        return render(request, template_name='track_detail.html', context={'track':current_track})
    if request.method == 'POST':
        return HttpResponse('qq')
    
@login_required(login_url='/login/')  
def cut_track_view(request, track_id):
    track = Track.objects.get(id=track_id)

    if request.method == "POST":
        form = Cut_Track_Form(request.POST, request.FILES)
        if form.is_valid():

            cut_track_instance = form.save(commit=False)
            cut_track_instance.user = request.user
            cut_track_instance.original_track = track
            cut_track_instance.created_at = timezone.now()

            start_time = form.cleaned_data['start_time']
            end_time = form.cleaned_data['end_time']

            cut_path = cut_mp3_with_mutagen(track.audio_file.path, start_time, end_time)
            with open(cut_path, 'rb') as f:
                cut_track_instance.cut_audio_file.save(
                    os.path.basename(cut_path), File(f), save=False
                )

            cut_track_instance.save()

            return redirect('user')
    else:
        form = Cut_Track_Form()

    return render(request, 'cut_track.html', {
        'form': form,
        'track': track,
    })
from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login , logout
from django.contrib.auth.models import User , Group
from .forms import Login_Form , Register_Form
from django.contrib.auth.decorators import login_required
from django.contrib import messages
from music_app.models import CutTrack, Track , Artist
from django.http import JsonResponse

def home(request):
    return render(request, template_name="home.html")

@login_required(login_url='/login/')
def user_view(request):
        current_cut_tracks=CutTrack.objects.all().filter(user_id=request.user.id)
        current_user=request.user
        
        return render(request, template_name='user.html', context={
            'user':current_user,
            'cut_tracks':current_cut_tracks,
            })

def login_view(request):
    if request.method == 'POST':
        form = Login_Form(request, data=request.POST)
        if form.is_valid():
            username = form.cleaned_data.get('username')
            password = form.cleaned_data.get('password')
            user = authenticate(request, username=username, password=password)
            if user is not None:
                login(request, user)
                return redirect('home')
    else:
        form = Login_Form()
    return render(request, 'login.html', {'form': form})

def register_view(request):
    if request.method=="GET":
        form=Register_Form()
        return render(request, template_name="register.html", context={'form':form})
    
    if request.method == 'POST':
        form=Register_Form(request.POST)
        if form.is_valid():
            user = form.save(commit=False)
            user.set_password(form.cleaned_data["password"])
            user.save()

            user_group = Group.objects.get(name="Listener")
            user.groups.add(user_group)
            messages.success(request, "Регистрация успешна! Войдите в систему.")
            return redirect('login')
        else:
            form = Register_Form()

    return render(request, "register.html", {"form": form})
        

@login_required(login_url='/login/')
def logout_view(request):
     logout(request)
     return redirect('login')

# @login_required
# def favorite(request, track_id):
#     if request.method=="POST":
#         track = Track.objects.get(id=track_id)

#         is_added = Favorite_Form.add_favorite(request.user, track)
#         if not is_added:
#             Favorite_Form.remove_favorite(request.user, track)

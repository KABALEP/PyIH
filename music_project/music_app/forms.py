from django import forms
from pydantic import ValidationError
from .models import Genre, Artist , Album , Track , CutTrack

class Genre_Form(forms.ModelForm):
    class Meta:
        model = Genre
        fields = ['name']
        widgets = {
            'name': forms.TextInput(attrs={'placeholder': 'Введите название жанра', 'class': 'form-control'}),
        }

class Artist_Form(forms.ModelForm):
    class Meta:
        model = Artist
        fields = ['name', 'bio', 'photo']
        widgets = {
            'name': forms.TextInput(attrs={'class': 'form-control'}),
            'bio': forms.Textarea(attrs={'class': 'form-control', 'rows': 4}),
            'photo': forms.FileInput(attrs={'class': 'form-control'}),
        }

    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user', None)
        super(Artist_Form, self).__init__(*args, **kwargs)
    
    def save(self, commit=True):
        artist = super().save(commit=False)
        if self.user:
            artist.user = self.user
        if commit:
            artist.save()
        return artist

class Album_Form(forms.ModelForm):
    class Meta:
        model = Album
        fields = ['title', 'artist', 'cover_image', 'release_date', 'genre']
        widgets = {
            'title': forms.TextInput(attrs={'class': 'form-control'}),
            'artist': forms.Select(attrs={'class': 'form-control'}),
            'cover_image': forms.FileInput(attrs={'class': 'form-control'}),
            'release_date': forms.DateInput(attrs={'class': 'form-control', 'type': 'date'}),
            'genre': forms.Select(attrs={'class': 'form-control'}),
        }

class Track_Form(forms.ModelForm):
    class Meta:
        model = Track
        fields = ['title', 'artist', 'track_image', 'genre', 'audio_file', 'release_date']
        widgets = {
            'title': forms.TextInput(attrs={'class': 'form-control'}),
            'artist': forms.Select(attrs={'class': 'form-control'}),
            'track_image': forms.FileInput(attrs={'class': 'form-control'}),
            'genre': forms.Select(attrs={'class': 'form-control'}),
            'audio_file': forms.FileInput(attrs={'class': 'form-control'}),
            'release_date': forms.DateInput(attrs={'class': 'form-control', 'type': 'date'}),
        }

class Cut_Track_Form(forms.ModelForm):
    class Meta:
        model = CutTrack
        fields = ['start_time', 'end_time']
        widgets = {
            'start_time': forms.NumberInput(attrs={
                'class': 'form-control',
                'placeholder': 'Начало (в секундах)',
                'min': 0,
            }),
            'end_time': forms.NumberInput(attrs={
                'class': 'form-control',
                'placeholder': 'Конец (в секундах)',
                'min': 0,
            })
        }

    def clean(self):
        cleaned_data = super().clean()
        start_time = cleaned_data.get('start_time')
        end_time = cleaned_data.get('end_time')

        if start_time is not None and end_time is not None and start_time >= end_time:
            raise ValidationError("Начало не может быть больше или равно концу.")
        
        return cleaned_data
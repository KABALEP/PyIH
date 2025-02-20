from django import forms
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.models import User
from django.db import models
from .models import Favorite
from music_app.models import Track

class Login_Form(AuthenticationForm):
    username = forms.CharField(
        label='Имя пользователя',
        max_length=150,
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Введите имя пользователя'})
    )
    password = forms.CharField(
        label='Пароль',
        widget=forms.PasswordInput(attrs={'class': 'form-control', 'placeholder': 'Введите пароль'})
    )

class Register_Form(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    confirm_password = forms.CharField(widget=forms.PasswordInput)

    class Meta:
        model = User
        fields = ["username","email", "password"]

    def clean(self):
        cleaned_data = super().clean()
        password = cleaned_data.get("password")
        confirm_password = cleaned_data.get("confirm_password")

        if password and confirm_password and password != confirm_password:
            raise forms.ValidationError("Пароли не совпадают")

        return cleaned_data

# class Favorite_Form(models.Model):
#     user = models.ForeignKey(User, on_delete=models.CASCADE, related_name="favorites")
#     track = models.ForeignKey(Track, on_delete=models.CASCADE, related_name="favorited_by")
#     added_at = models.DateTimeField(auto_now_add=True)

#     class Meta:
#         unique_together = ('user', 'track')

#     def __str__(self):
#         return f"{self.user.username} * {self.track.title}"

#     @classmethod
#     def add_favorite(cls, user, track):
#         favorite, created = cls.objects.get_or_create(user=user, track=track)
#         return created

#     @classmethod
#     def remove_favorite(cls, user, track):
#         return cls.objects.filter(user=user, track=track).delete()

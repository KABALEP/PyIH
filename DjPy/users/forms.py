from django import forms
from users.models import User

class login_form(forms.Form):
    username=forms.CharField(label='Username', max_length=100)
    password=forms.CharField(label='password', widget=forms.PasswordInput)

class user_register_form(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)
    confirm_password = forms.CharField(widget=forms.PasswordInput)

    class Meta:
        model = User
        fields = ["username", "password", "first_name", "last_name","email"]

    def clean(self):
        cleaned_data = super().clean()
        password = cleaned_data.get("password")
        confirm_password = cleaned_data.get("confirm_password")

        if password and confirm_password and password != confirm_password:
            raise forms.ValidationError("Пароли не совпадают")

        return cleaned_data
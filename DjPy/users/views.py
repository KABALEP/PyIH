from django.http import HttpResponse
from django.shortcuts import render , redirect
from django.contrib.auth.models import User , Group
from django.contrib.auth import authenticate, login , logout
from django.contrib import messages
from django.contrib.auth.decorators import login_required
import booking.models as booking_models
import users.forms as user_forms

def home_page(request):
    return render(request, template_name="home.html")

@login_required(login_url='/login/')
def user_page(request):
        current_user=request.user
        user_bookings = booking_models.Booking.objects.filter(user=current_user)
        return render(request, 'user_page.html', {
        'user': current_user,
        'user_bookings': user_bookings
        })

@login_required(login_url='/login/')
def specific_user(request, user_id):
    return HttpResponse("QQ")

def login_page(request):
    if request.method == "GET":
        form_login=user_forms.login_form()
        return render(request, template_name="login.html", context={'form':form_login})
    else:
        form_login=user_forms.login_form(request.POST)
        if form_login.is_valid():
            user_name = form_login.cleaned_data["username"]
            password = form_login.cleaned_data["password"]

            user = authenticate(request, username=user_name, password=password)
        
            if user is not None:
                login(request, user)
                return render(request, template_name="home.html")
            else:
                messages.error(request, "Неверное имя пользователя или пароль.")
                return render(request, template_name="login.html", context={'form':form_login})
        
@login_required(login_url='/login/')
def logout_page(request):
    form_login=user_forms.login_form(request.POST)
    if request.user.is_authenticated:
        logout(request)
        return render(request, template_name="login.html", context={'form':form_login})

def register_page(request):
    if request.method=="GET":
        register_form=user_forms.user_register_form()
        return render(request, template_name="register.html", context={'register_form':register_form})
    else:
        form_login=user_forms.login_form()
        form = user_forms.user_register_form(request.POST)
        if form.is_valid():
            user = form.save(commit=False)
            user.set_password(form.cleaned_data["password"])
            user.save()

            user_group = Group.objects.get(name="Client")
            user.groups.add(user_group)

            messages.success(request, "Регистрация успешна! Войдите в систему.")
            return render(request, template_name="login.html", context={'form':form_login})
        else:
            form = user_forms.user_register_form()

    return render(request, "register.html", {"form": form})
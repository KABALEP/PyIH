from django.shortcuts import redirect, render
from django.http import HttpResponse, HttpResponseForbidden
from django.contrib import messages
from django.contrib.auth.models import User , Group
from django.contrib.auth.decorators import login_required
from trainer.utils import booking_time_discovery
from dateutil import parser
import users.forms as user_forms
import trainer.forms as trainer_forms

import booking.models as booking_models
import trainer, datetime
import trainer.models
import trainer.utils

def all_trainers(request):
    trainers = User.objects.filter(groups__name='Trainer')
    return render(request, 'all_trainers.html', {'trainers': trainers})

def trainers_by_category(request, category):
    return HttpResponse("QQ")

@login_required(login_url='/login/')
def trainer_page(request, trainer_id):
        if request.method == 'GET':
            service_category=trainer.models.Category.objects.all()
            my_services=trainer.models.Service.objects.filter(trainer_id=trainer_id)
            current_trainer= User.objects.get(id=trainer_id)
            current_user=request.user
            return render(request, template_name="trainer.html", context={"categories" : service_category,
                                                                        "services": my_services,
                                                                        "trainer": current_trainer,
                                                                        "user":current_user})
        else:
            if 'service_added' in request.POST:
                trainer_model=User.objects.get(pk=trainer_id)
                trainer_data= trainer.models.TrainerDescription(trainer= trainer_model)
                trainer_schedule=trainer.models.TrainerSchedule.objects.filter(trainer=trainer_model)
                return render(request, template_name='account.html', context={"trainer_data": trainer_data, "trainer_schedule": trainer_schedule})

@login_required(login_url='/login/')
def create_service(request, trainer_id):
    if request.user.groups.filter(name='Trainer').exists():
        if request.method == 'POST':
            form_data= request.POST
            service_cat=trainer.models.Category.objects.get(pk=form_data["category"])
            service= trainer.models.Service(
                category=service_cat,
                trainer=request.user,
                price=form_data["price"],
                level=form_data["level"],
                duration=form_data["duration"]
            )
            service.save()
        return redirect("trainer_page", trainer_id=trainer_id)
    else:
        return redirect("trainer_page", trainer_id=trainer_id)

@login_required(login_url='/login/')
def trainer_services(request, trainer_id, service_id):
    current_trainer=User.objects.get(id=trainer_id)
    specific_service=trainer.models.Service.objects.get(id=service_id)

    if request.method == 'GET':
        available_times= []
        today=datetime.datetime.now()
        days_from_now=1

        while days_from_now <= 2:
            cur_date=datetime.datetime(today.year, today.month, today.day) + datetime.timedelta(days=days_from_now)

            trainer_bookings = booking_models.Booking.objects.filter(
                trainer=current_trainer, datetime_start__date=cur_date
            ).all()
            bookings_list = [(itm.datetime_start, itm.datetime_end) for itm in trainer_bookings]

            trainer_schedule = trainer.models.TrainerSchedule.objects.filter(
                trainer=current_trainer, datetime_start__date=cur_date
            ).values_list('datetime_start', 'datetime_end')

            for start_schedule, end_schedule in trainer_schedule:
                available_times += booking_time_discovery(
                    start_schedule, end_schedule, bookings_list, specific_service.duration
                )
            days_from_now+=1

        return render(request,
                      template_name='trainer_service_page.html',
                      context= {'available_times':available_times, 'specific_service':specific_service })
    else:
        booking_start=parser.parse(request.POST.get('training_start'))
        current_user=User.objects.get(id=request.user.id)
        booking_models.Booking.objects.create(trainer=current_trainer,
                                              user=current_user,
                                              service=specific_service,
                                              datetime_start=booking_start,
                                              datetime_end=booking_start+datetime.timedelta(minutes=specific_service.duration))
        return redirect("trainer_page", trainer_id=trainer_id)
        
def trainer_register(request):
    if request.method=="GET":
        register_form=trainer_forms.trainer_register_form()
        return render(request, template_name="register.html", context={'register_form':register_form})
    else:
        form_login=user_forms.login_form()
        form = trainer_forms.trainer_register_form(request.POST)
        if form.is_valid():
            user = form.save(commit=False)
            user.set_password(form.cleaned_data["password"])
            user.save()

            user_group = Group.objects.get(name="Client")
            user.groups.add(user_group)

            messages.success(request, "Регистрация успешна! Войдите в систему.")
            return render(request, template_name="login.html", context={'form':form_login})
        else:
            form = trainer_forms.trainer_register_form()

    return render(request, "register.html", {"form": form})

# @login_required
# def trainers_service_booking(request, trainer_id, service_id, booking_id):
    
#     current_trainer = User.objects.get(id=trainer_id)
#     specific_service = trainer.models.Service.objects.get(id=service_id)
#     booking_instance = booking_models.Booking.objects.get(id=booking_id , trainer=current_trainer, service=specific_service)

#     if request.user != booking_instance.user and request.user != current_trainer:
#         return HttpResponseForbidden("You do not have permission to view this booking.")

#     return render(request, 'trainer_service_booking_page.html', {
#         'trainer': current_trainer,
#         'service': specific_service,
#         'booking': booking_instance,
#     })

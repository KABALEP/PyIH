from django.shortcuts import redirect, render
from django.http import HttpResponse
from django.contrib.auth.decorators import login_required
import booking.models as booking_models

@login_required(login_url='/login/')
def booking_page(request, booking_id):
    booking_data=booking_models.Booking.objects.get(id=booking_id)
    return render(request, template_name='booking_page.html', context={'booking':booking_data})

@login_required(login_url='/login/')
def booking_page_cancel(request, booking_id):
    try:
        booking = booking_models.Booking.objects.get(id=booking_id)
    except booking_models.Booking.DoesNotExist:
        return HttpResponse("Booking not found.", status=404)

    if request.user == booking.trainer:
        booking.delete()
        return redirect("user_page")
    else:
        return HttpResponse("You don't have permission to cancel this booking.", status=403)

@login_required(login_url='/login/')
def booking_page_accept(request, booking_id):
    try:
        booking = booking_models.Booking.objects.get(id=booking_id)
    except booking_models.Booking.DoesNotExist:
        return HttpResponse("Booking not found.", status=404)

    if request.user == booking.trainer:
        booking.status = True 
        booking.save()
        return redirect("user_page")
    else:
        return HttpResponse("You don't have permission to accept this booking.", status=403)

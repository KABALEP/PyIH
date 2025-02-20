from django.db import models
from django.contrib.auth.models import User
from trainer.models import Service

class Booking(models.Model):
    user=models.ForeignKey(User, on_delete=models.CASCADE, related_name="bookings")
    trainer=models.ForeignKey(User, on_delete=models.CASCADE, related_name="trainer_bookings")
    datetime_start=models.DateTimeField()
    datetime_end=models.DateTimeField()
    service=models.ForeignKey(Service, on_delete=models.CASCADE)
    status=models.BooleanField(default=False)

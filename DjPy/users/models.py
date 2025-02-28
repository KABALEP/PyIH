from django.db import models
from django.contrib.auth.models import User

class Rating(models.Model):
    author=models.ForeignKey(User, on_delete=models.CASCADE, related_name="author")
    recipient=models.ForeignKey(User, on_delete=models.CASCADE, related_name="recipient")
    rate=models.IntegerField(default=0)
    text=models.TextField()

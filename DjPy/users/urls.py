from django.urls import path
from . import views

urlpatterns = [
    path('user/', views.user_page, name='user_page'),
    path('user/<int:user_id>/', views.specific_user, name='specific_user'),
]
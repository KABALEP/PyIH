from django.urls import path
from . import views

urlpatterns = [
    path('booking/<int:booking_id>/', views.booking_page, name='booking_page'),  # Страница бронирования
    path('booking/<int:booking_id>/cancel/', views.booking_page_cancel, name='booking_page_cancel'),  # Отмена бронирования
    path('booking/<int:booking_id>/accept/', views.booking_page_accept, name='booking_page_accept'),  # Подтверждение бронирования
]
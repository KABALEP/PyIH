from django.urls import path
from . import views

urlpatterns = [
    path('trainers/', views.all_trainers, name='all_trainers'),  # Все тренеры
    path('trainers/category/<str:category>/', views.trainers_by_category, name='trainers_by_category'),  # Тренеры по категории
    path('trainers/<int:trainer_id>/', views.trainer_page, name='trainer_page'),  # Страница тренера
    path('trainers/<int:trainer_id>/services/<int:service_id>/', views.trainer_services, name='trainer_services'),  # Услуги тренера
    # path('trainers/<int:trainer_id>/services/<int:service_id>/booking/<int:booking_id>/', views.trainers_service_booking, name='trainers_service_booking'),  # Подтверждение или отмена
    path('trainers/<int:trainer_id>/services/create/', views.create_service, name='create_service'),
]
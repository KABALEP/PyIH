from django.urls import path
from .views import user_view 
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    path('user/', user_view, name='user'),
    # path('favorite/<int:track_id>/', favorite, name="add_favorite"),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
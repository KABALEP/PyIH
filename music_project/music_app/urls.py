from django.urls import path
from .views import add_genre_view , genre_list_view , add_artist_view , artist_view, create_album_view , album_view , tracks_view , add_track_view , track_detail_view, cut_track_view
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    path('genres/add/', add_genre_view, name='add_genre'),
    path('genres/', genre_list_view, name='genre_list'),
    path('artist/<int:artist_id>',artist_view ,name='artist'),
    path('artist/create/', add_artist_view, name='add_artist'),
    path('album/create/', create_album_view, name='album_create'),
    path('album/', album_view, name='album_list'),
    path('track/',tracks_view, name='track_list'),
    path('track/add/', add_track_view, name='add_track'),
    path('track/<int:track_id>/', track_detail_view, name='track_detail'),
    path('track/<int:track_id>/cut/', cut_track_view, name='cut_track')
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
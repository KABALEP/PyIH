from django.db import models
from django.contrib.auth.models import User

class Genre(models.Model):
    name = models.CharField(max_length=100, unique=True)

    def __str__(self):
        return self.name

class Artist(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='artist_profile')
    name = models.CharField(max_length=255)
    bio = models.TextField(blank=True, null=True)
    photo = models.ImageField(upload_to="artists/", blank=True, null=True)

    def __str__(self):
        return self.name

class Album(models.Model):
    title = models.CharField(max_length=255)
    artist = models.ForeignKey(Artist, on_delete=models.CASCADE, related_name="albums")
    cover_image = models.ImageField(upload_to="albums/", blank=True, null=True)
    release_date = models.DateField()
    genre = models.ForeignKey(Genre, on_delete=models.SET_NULL, null=True, blank=True)

    def __str__(self):
        return f"{self.title} - {self.artist.name}"

class Track(models.Model):
    title = models.CharField(max_length=255)
    album = models.ForeignKey(Album, on_delete=models.CASCADE, related_name="tracks", blank=True, null=True)
    artist = models.ForeignKey(Artist, on_delete=models.CASCADE, related_name="tracks")
    genre = models.ForeignKey(Genre, on_delete=models.SET_NULL, null=True, blank=True)
    audio_file = models.FileField(upload_to="music/")
    duration = models.PositiveIntegerField(help_text="Длительность в секундах")
    track_image = models.ImageField(upload_to="music/track_image/", blank=True, null=True)
    release_date = models.DateField()
    plays = models.PositiveIntegerField(default=0)

    def __str__(self):
        return f"{self.title} - {self.artist.name}"

class Playlist(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name="playlists")
    name = models.CharField(max_length=255)
    tracks = models.ManyToManyField(Track, related_name="playlists")
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.name} ({self.user.username})"

class CutTrack(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE, related_name="cut_tracks")
    original_track = models.ForeignKey(Track, on_delete=models.CASCADE, related_name="cuts")
    cut_audio_file = models.FileField(upload_to="cut_tracks/")
    start_time = models.PositiveIntegerField(help_text="Начало в секундах")
    end_time = models.PositiveIntegerField(help_text="Конец в секундах")
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.user.username} - {self.original_track.title} (cut {self.start_time}-{self.end_time})"

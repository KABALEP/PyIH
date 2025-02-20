import os
from mutagen.mp3 import MP3
from django.conf import settings


def cut_mp3_with_mutagen(input_path, start_sec, end_sec):

    audio = MP3(input_path)
    bitrate = audio.info.bitrate // 8 

    start_byte = int(start_sec * bitrate)
    end_byte = int(end_sec * bitrate)

    base_name, ext = os.path.splitext(os.path.basename(input_path))
    output_dir = os.path.join(settings.MEDIA_ROOT, 'cut_tracks')
    os.makedirs(output_dir, exist_ok=True)
    output_path = os.path.join(output_dir, f"{base_name}_cut_{start_sec}_{end_sec}{ext}")

    with open(input_path, 'rb') as infile:
        infile.seek(start_byte)
        with open(output_path, 'wb') as outfile:
            outfile.write(infile.read(end_byte - start_byte))

    return output_path
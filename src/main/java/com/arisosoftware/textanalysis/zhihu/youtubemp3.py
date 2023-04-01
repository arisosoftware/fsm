# pytube library to download the YouTube video and moviepy library to convert it to an MP3 file in pure Python.
# In the code above, we're using the pytube library to download the YouTube video and the moviepy library to convert it to an MP3 file. We're first downloading the audio-only stream of the video, then using moviepy to convert the downloaded video to an MP3 file. We're also deleting the original video file to save disk space.
# Note that pytube and moviepy libraries may need to be installed separately. You can install them using the following command in your terminal:
############## pip3 install pytube moviepy
import os
from pathlib import Path
from pytube import YouTube
from moviepy.video.io.VideoFileClip import VideoFileClip
from moviepy.audio.AudioClip import AudioFileClip

# Replace the YouTube URL with the URL of the video you want to download
url = 'https://www.youtube.com/watch?v=dQw4w9WgXcQ'

# Download the YouTube video
yt = YouTube(url)
stream = yt.streams.filter(only_audio=True).first()
stream.download()

# Convert the video to MP3
video_file = VideoFileClip(f"{yt.title}.mp4")
audio_file = video_file.audio
audio_file.write_audiofile(f"{yt.title}.mp3")

# Delete the original video file
os.remove(f"{yt.title}.mp4")


from pytube import YouTube
import os
from pathlib import Path


def youtube2mp3 (url,outdir):
    # url input from user
    yt = YouTube(url)

    ##@ Extract audio with 160kbps quality from video
    video = yt.streams.filter(abr='160kbps').last()

    ##@ Downloadthe file
    out_file = video.download(output_path=outdir)
    base, ext = os.path.splitext(out_file)
    new_file = Path(f'{base}.mp3')
    os.rename(out_file, new_file)
    ##@ Check success of download
    if new_file.exists():
        print(f'{yt.title} has been successfully downloaded.')
    else:
        print(f'ERROR: {yt.title}could not be downloaded!')



import youtube_dl
def run():
    video_url = input("please enter youtube video url:")
    video_info = youtube_dl.YoutubeDL().extract_info(
        url = video_url,download=False
    )
    filename = f"{video_info['title']}.mp3"
    options={
        'format':'bestaudio/best',
        'keepvideo':False,
        'outtmpl':filename,
    }

    with youtube_dl.YoutubeDL(options) as ydl:
        ydl.download([video_info['webpage_url']])

    print("Download complete... {}".format(filename))

if __name__=='__main__':
    run()


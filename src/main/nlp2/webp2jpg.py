# -*- coding: utf-8 -*-
import io
import re
import sys
import os
import difflib
from collections import OrderedDict
from datetime import datetime
from PIL import Image


def convert_webp_to_jpg(folder_path):
    for filename in os.listdir(folder_path):
        if filename.endswith(".webp"):
            webp_path = os.path.join(folder_path, filename)
            jpg_path = os.path.join(folder_path, os.path.splitext(filename)[0] + ".jpg")

            # Open the WebP image and save it as JPG
            image = Image.open(webp_path)
            image.save(jpg_path, "jpg")

            # Optionally, you can also delete the original WebP file
            # os.remove(webp_path)

def convert_one_webp_to_jpg(filename):
    if filename.endswith(".webp"):
        jpg_path = filename + ".jpg"
        # Open the WebP image and save it as JPG
        image = Image.open(filename)
        image.save(jpg_path, "JPEG")
        os.remove(filename)
        # Optionally, you can also delete the original WebP file
        


# Example usage
prgname = sys.argv[0]

for theFile in sys.argv:
    if (theFile != prgname):
        print(theFile)
        convert_one_webp_to_jpg(theFile)

# In this code, the convert_webp_to_jpg() function takes the folder path as input and loops through all the files in that folder. It checks if the file has a .webp extension and, if so, converts it to JPG format using the Image.open() and Image.save() methods from the Pillow library.

# Make sure to replace /path/to/webp/images with the actual path to the folder containing your WebP images. The converted JPG images will be saved in the same folder with the same filename but a .jpg extension.

# Note: Before running the code, ensure that you have the Pillow library installed. You can install it using pip install pillow.
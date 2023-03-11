# -*- coding: utf-8 -*-
import io
import re
import sys
# Requirement: 

if len(sys.argv) < 2:
    print("Usage: python script.py input_file ")
    sys.exit(1)

input_file = sys.argv[1]
errno = 1
with open(input_file, "rb") as f:
    # Read the file as bytes
    data = f.read()

    # Split the file into lines
    lines = data.split(b'\n')

    # Iterate over the lines to find the byte
    for i, line in enumerate(lines):
        if b'\xe2' in line:
            print(f"Line {i+1} contains byte 0xe2 #{errno}")
            errno = errno+1
        if errno>100:
            break


with open(input_file, 'rb') as f:
    # Read the file as bytes
    byte_data = f.read()

# Decode the byte data as UTF-8
text_data = byte_data.decode('utf-8', errors='replace')

# Save the text data as a UTF-8 encoded file
with open('output_file', 'w', encoding='utf-8') as f:
    f.write(text_data)

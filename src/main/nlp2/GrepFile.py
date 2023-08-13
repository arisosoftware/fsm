import sys
import os

def search_and_concat(directory, keyword, output_file):
    # Use a list to store the matching filenames
    filenames = []

    # Traverse the directory and search for the keyword in files
    for root, _, files in os.walk(directory):
        for file in files:
            filepath = os.path.join(root, file)
            with open(filepath, 'r') as f:
                if keyword in f.read():
                    filenames.append(filepath)

    # Concatenate the contents of the files into the output file
    with open(output_file, 'w') as output:
        for filename in filenames:
            with open(filename, 'r') as file:
                output.write(file.read())

    print(f"Concatenated the contents of matching files into {output_file}")

# Retrieve command-line arguments
if len(sys.argv) != 4:
    print("Usage: python3 GrepFile.py directory keyword output_file")
    sys.exit(1)

directory = sys.argv[1]
keyword = sys.argv[2]
output_file = sys.argv[3]

# Call the search_and_concat function with the provided arguments
search_and_concat(directory, keyword, output_file)



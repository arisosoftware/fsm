import os
import re
import sys

def is_valid_file_path_windows(path):
    # Check if the path matches the Windows filename and pathname validation rules
    pattern = r'^[a-zA-Z0-9\s!@#$%&()-_^\'.{}~]+(\.[a-zA-Z0-9\s!@#$%&()-_^\'.{}~]+)?$'
    return re.match(pattern, path) is not None

def get_invalid_files_in_directory(directory):
    invalid_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            file_path = os.path.join(root, file)
            if not is_valid_file_path_windows(file_path):
                invalid_files.append(file_path)
    return invalid_files

# Usage
if len(sys.argv) < 2:
    print("Please provide the directory path as an argument.")
    sys.exit(1)

directory_path = sys.argv[1]
invalid_files = get_invalid_files_in_directory(directory_path)
print("Invalid files:")
for file_path in invalid_files:
    print(file_path)


# In this updated script, the directory path is taken as a command-line argument using sys.argv. The script checks if the required argument is provided and exits with an error message if not. Then, it calls the get_invalid_files_in_directory() function to retrieve the list of invalid file paths based on the Windows validation rules. Finally, it prints the list of invalid files.

# To run the script, pass the directory path as a command-line argument like this:

# bash
# Copy code
# python script.py /path/to/directory
import os
import re

def is_valid_file_path_windows(path):
    # Check if the path matches the Windows filename and pathname validation rules
    pattern = r'^[a-zA-Z0-9\s!@#$%&()-_^\'.{}~]+(\.[a-zA-Z0-9\s!@#$%&()-_^\'.{}~]+)?$'
    return re.match(pattern, path) is not None

def validate_files_in_directory(directory):
    for root, dirs, files in os.walk(directory):
        for file in files:
            file_path = os.path.join(root, file)
            if is_valid_file_path_windows(file_path):
                #print(f"File path '{file_path}' is valid.")
            else:
                print(f"File path '{file_path}' is invalid.")

# Usage example
directory_path = '/media/airsoft/512GB/Lexar/novel/novel'
validate_files_in_directory(directory_path)

import os
import subprocess

def remove_question_mark(folder_path):
    # Check if the provided path is a valid directory
    if not os.path.isdir(folder_path):
        print("Invalid directory path!")
        return

    # Get a list of all files in the directory
    files = os.listdir(folder_path)

    # Iterate through each file in the directory
    for filename in files:

        print(f"test {filename}w")

        new_name = filename.replace("?", "")
        new_name = filename.replace("？", "")
        new_name = filename.replace(" ", "")
        new_name = filename.replace("　", "")

        if new_name != filename:
            # New name without the '?'
            # Generate full paths for the old and new names
            old_path = os.path.join(folder_path, filename)
            new_path = os.path.join(folder_path, new_name)

            # Rename the file using git mv
            try:
                #subprocess.run(["git", "mv", old_path, new_path], check=True)
                print(f"Renamed {filename} to {new_name}")
            except subprocess.CalledProcessError as e:
                print(f"Error renaming {filename}: {e}")

if __name__ == "__main__":
    folder_path = "/media/airsoft/512GB/Lexar/novel/novel/T2/tmpsync"  # Replace with the actual folder path
    remove_question_mark(folder_path)

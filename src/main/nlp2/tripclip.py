import pyperclip
import logging
import re
import datetime

def process_text(input_text):

    input_text = input_text.replace("Total Trip Time (including layovers)", "Total_Time:")
    input_text = input_text.replace("Select this departure", "")
    input_text = input_text.replace("Select this return", "")
    input_text = input_text.replace("SELECT", "")
    input_text = input_text.replace("(long connection)", "")
    input_text = input_text.replace("Final total price (taxes included)", "")
    input_text = input_text.replace("Set a fare alert for this flight", "")

    # Split the input into lines
    lines = input_text.split('\n')


    # Initialize a variable to store non-blank lines
    txline = ""
    cleaned_text = ""
    skip_lines = False

    if "results found" in input_text:
         skip_lines = True

    # Process each line
    for line in lines:
        if skip_lines:
            if "results found" in line:
                # Stop skipping lines when "results found" is encountered
                skip_lines = False
            continue
        else:
            if "* In-Flight services" in line:
                # Stop skipping lines when "results found" is encountered
                skip_lines = True
                break

        # Remove leading and trailing whitespace
        trimmed_line = line.strip()

        if trimmed_line:  # Check if the trimmed line is not empty
            # Append the non-blank line to txline
            txline += trimmed_line + '\t'
        else:
            if txline:
                # If txline is not empty, print it
                #print(txline)
                cleaned_text = cleaned_text +'\n' + txline
                # Reset txline to blank
                txline = ""

    # Print any remaining content in txline (if not empty)
    if txline:
        #print(txline)
        cleaned_text = cleaned_text +'\n' + txline
    return cleaned_text

def save_clipboard_as_html():
    # Get the text from the clipboard
    clipboard_text = pyperclip.paste()
    # Print the clipboard text to the console
    normaltxt = process_text(clipboard_text)
    print("Text:")
    print(normaltxt)
    # Append the clipboard text to the log file
    # Get the current timestamp
    timestamp ='\n\n'+ datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')+'\n'
    # Open the file in "append" mode
    with open(file_path, "a") as file:
        # Append the text to the file
        file.write(timestamp)
        file.write(normaltxt)

    # Close the file


# ------------------------main 
file_path = 'clipboard_log.txt'

save_clipboard_as_html()

#while True:
    # Wait for user input (Enter key press or "quit" to exit)
    # user_input = input("Press Enter to save clipboard content as HTML (or enter 'quit' to exit):")
    
    #if user_input.lower() == "quit":
    #    break
    # Trigger the function to get clipboard, convert to HTML, and save to file
    #save_clipboard_as_html()
 



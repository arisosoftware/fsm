import pyperclip
import logging
import re
import datetime

#To transform the input "12h 45m 候机 香港" into "香港候机 12小时 45m,"
def transform_text(input_text):
    # Split the input into words and numbers
    words_and_numbers = re.findall(r'\d+[hH]\s*\d+[mM]|\S+', input_text)

    # Split the numbers and words
    numbers = [word for word in words_and_numbers if re.match(r'\d+[hH]\s*\d+[mM]', word)]
    words = [word for word in words_and_numbers if not re.match(r'\d+[hH]\s*\d+[mM]', word)]

    # Create the transformed text
    transformed_text = ' '.join(words) + ' ' + ' '.join(numbers)

    return transformed_text



def format_datetime(date_str, time_str):
    # Combine the date and time strings
    datetime_str = f"{date_str} {time_str}"
    # Parse the datetime string and format it
    datetime_obj = datetime.datetime.strptime(datetime_str, "%a, %b %d %I:%M%p")
    return datetime_obj.strftime("%m-%d %H:%M")

def reorderText(input_text):
    # Split the line by tabs
    columns = input_text.split('\t')
    if len(columns) > 7:
        # Format date and time columns 2 and 3
        date_time_1 = format_datetime(columns[3], columns[2])
        date_time_2 = format_datetime(columns[6], columns[5])

        reordered_columns_cde = [
            columns[0], columns[1], columns[4], date_time_1, columns[7], date_time_2
        ]

        if  len(columns) > 9:
            
        elif :
            pass
        else:
            reordered_columns_cde.extend(columns[8:])


        # Join the reordered columns with tabs
        line =""
        line = '\t'.join(reordered_columns_cde)


        return line
    else:

        return input_text

def process_text2(input_text):

    input_text = re.sub("\nTotal_Time","\tTotal_Time",input_text)
    # Split the input into lines
    lines = input_text.split('\n')
    # Initialize a variable to store non-blank lines
    cleaned_text = ""

    # Process each line
    for line in lines:
        if "CHEAPEST" in line:
            continue
        else:
            if "--END--" in line:
                break
        line = line.replace("This is a nearby airport","")
        line = line.replace("Economy	Nonstop","飞行")
        line = line.replace("Vancouver","温哥华")
        line = line.replace("Hong Kong","香港")
        line = line.replace("Xiamen","厦门")
        line = line.replace("Toronto","多伦多")
        line = line.replace("layover in","候机")

        line = line.replace("Montreal","蒙特利")
        line = line.replace("Tokyo","东京")
        line = line.replace("Shanghai","上海")
 
        line = line.replace("Unselect this return","")
        line = reorderText(line)
        
        cleaned_text = cleaned_text +'\n' + line

        # if "Total_" in line:
        #     cleaned_text = cleaned_text +'\n'

    return cleaned_text


def process_text(input_text):

    input_text = input_text.replace("Total Trip Time (including layovers)", "Total_Time:")
    input_text = input_text.replace("Select this departure", "")
    input_text = input_text.replace("Select this return", "")
    input_text = input_text.replace("SELECT", "")
    input_text = input_text.replace("(18.34 km. away from YTZ)", "")
    input_text = input_text.replace("(long connection)", "")
    input_text = input_text.replace("(short connection)", "")
    
    input_text = input_text.replace("Operated by Air Canada", ".")
    input_text = input_text.replace("(taxes) =", "\n")
    input_text = input_text.replace("Final total price (taxes included)", "含税价.")
    input_text = input_text.replace("Set a fare alert for this flight", "--END--")
    input_text = input_text.replace("In-Flight services", "--END--")

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
            if "--END--" in line:
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
    normaltxt = process_text2(normaltxt)
    print(normaltxt)
    timestamp ='\n\n'+ datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')+'\n'
    # Open the file in "append" mode
    with open(file_path, "a") as file:
        # Append the text to the file
        file.write(timestamp)
        file.write(normaltxt)
    # then the file will be Closed automatic, then copy to clipboard again
    pyperclip.copy(normaltxt)


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
 



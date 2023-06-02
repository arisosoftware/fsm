import pyperclip
from bs4 import BeautifulSoup
from datetime import datetime

# you need to 
# pip3 install clipboard
# pip3 install tkinter
# pip3  install pyperclip
# pip3  install beautifulsoup4


def get_clipboard_as_html():
    clipboard_text = pyperclip.paste()
    soup = BeautifulSoup(clipboard_text, 'html.parser')
    clipboard_html = soup.prettify()
    return clipboard_html

def save_clipboard_as_html():
    # Get clipboard content as HTML
    clipboard_html = get_clipboard_as_html()

    # Generate timestamp for the filename
    timestamp = datetime.now().strftime("_%Y%m%d_%H%M%S")

    # Save clipboard content as HTML to a file
    label = f"Time_{timestamp}_________________________________\n"
    #     file.write(clipboard_html)
    with open(logfile_path, mode="a", encoding="utf-8") as file:
        file.write(label)
        file.write(clipboard_html)
    print(f"Clipboard content saved as {label}")


timestamp = datetime.now().strftime("%H%M%S")
logfile_path = f"ZhihuLog{timestamp}.txt" 
# Endless loop
while True:
    # Wait for user input (Enter key press or "quit" to exit)
    user_input = input("Press Enter to save clipboard content as HTML (or enter 'quit' to exit):")
    
    if user_input.lower() == "quit":
        break
    
    # Trigger the function to get clipboard, convert to HTML, and save to file
    save_clipboard_as_html()
 





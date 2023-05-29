import clipboard
from datetime import datetime

def save_clipboard_as_html():
    # Get clipboard content as HTML
    clipboard_html = clipboard.paste()

    # Generate timestamp for the filename
    timestamp = datetime.now().strftime("%H%M%S")

    # Save clipboard content as HTML to a file
    filename = f"{timestamp}.html"
    with open(filename, "w", encoding="utf-8") as file:
        file.write(clipboard_html)
    
    print(f"Clipboard content saved as {filename}")

# Endless loop
while True:
    # Wait for user input (Enter key press or "quit" to exit)
    user_input = input("Press Enter to save clipboard content as HTML (or enter 'quit' to exit):")
    
    if user_input.lower() == "quit":
        break
    
    # Trigger the function to get clipboard, convert to HTML, and save to file
    save_clipboard_as_html()

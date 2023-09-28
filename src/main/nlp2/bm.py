import os
import urllib.request
from bs4 import BeautifulSoup
import hashlib

FileId = 100000

# Function to download HTML content from a URL
def download_html(url):
    try:
        response = urllib.request.urlopen(url, timeout=3)
        return response.read()
        # html_content = response.read().decode("utf-8")
        # return html_content
    except Exception as e:
        print(f"Failed to download {url}: {str(e)}")
        return None

def calculate_md5_hex(input_string):
    # Create an MD5 hash object
    md5_hash = hashlib.md5()

    # Update the hash object with the bytes of the input string
    md5_hash.update(input_string.encode('utf-8'))

    # Calculate the MD5 hash as a hexadecimal string
    md5_hex_string = md5_hash.hexdigest()

    return md5_hex_string

# # Example usage:
# input_string = "Hello, World!"
# md5_result = calculate_md5_hex(input_string)
# print("MD5 Hex String:", md5_result)







# ----------------------main route -----------


# Read the existing bookmark.htm file
bookmark_file = "bookmarks_9_28_23.html"
with open(bookmark_file, "r", encoding="utf-8") as f:
    lines = f.readlines()



# Create a new bookmark content with valid anchor tags
new_bookmark_content = ""
for line in lines:
    # Parse the line with BeautifulSoup
    soup = BeautifulSoup(line, "html.parser")
    
    # Find all anchor tags (links) in the line
    a_tags = soup.find_all("a")
    
    # If there are no anchor tags, copy the line as is
    if not a_tags:
        new_bookmark_content += line
    else:
        for a_tag in a_tags:
            url = a_tag.get("href")
            label = a_tag.string
            if url:
                url_md5 = calculate_md5_hex(url)    
                downloadPage = f'./downloads/F{url_md5}.htm'    
                if not os.path.exists(downloadPage):
                    html_content = download_html(url)
                    if html_content is not None:
                        # Output the anchor tag with URL and label
                        FileId +=1
                        new_bookmark_content += f'<a href="{url}">{label}</a>\n'
                        
                        
                        print ( f"{FileId}  {url_md5}  success {url} ")
                        with open(downloadPage, "wb") as fsave:
                                fsave.write(html_content)

# Rewrite the bookmark.htm file with valid anchor tags
with open(bookmark_file, "w", encoding="utf-8") as f:
    f.write(new_bookmark_content)

print("Updated bookmark.htm with valid anchor tags.")

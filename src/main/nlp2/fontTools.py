from fontTools.ttLib import TTFont

font_path = '/path/to/NotoSansCJK-Regular.ttc'
font = TTFont(font_path)

# Get the name table
name_table = font['name']

# Find the name record with the family name
family_name = None
for record in name_table.names:
    if record.nameID == 1:
        family_name = record.string.decode('utf-8')
        break

# Print the family name
if family_name:
    print('The font family name is:', family_name)
else:
    print('The font family name could not be found.')


# In this example, we first create a TTFont object by passing the path to the TTF file. We then get the name table from the font object, which contains information about the font's name. We search through the list of name records to find the one with the name ID 1, which corresponds to the font family name. Finally, we decode the string value of the record using UTF-8 encoding and print it out.

# pip3 install fontTools
# This will download and install fontTools and its dependencies from the Python Package Index (PyPI). After installation, you can import the fontTools module in your Python script to use it.

#If you are getting a ModuleNotFoundError with the message "'fontTools.ttLib' is not a package", it's possible that there is a version mismatch between the installed fontTools package and the version of Python you are using.

# Try running the following command to check if fontTools is installed:

# pip list | grep fonttools
# If fontTools is listed, then try upgrading it to the latest version by running:

# pip install --upgrade fonttools
# If fontTools is not listed, then try installing it again by running:

# pip install fonttools
# If you continue to encounter issues, you may want to try uninstalling and then reinstalling the package.


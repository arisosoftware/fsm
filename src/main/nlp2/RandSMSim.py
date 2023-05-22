import random

# Define a dictionary to store the message templates for different companies
message_templates = {
    "FACEBOOK": ["Use {code} to verify your {account} account.",
                 "{code} is your {account} code. don't share it."],
    "Amazon": ["{code} is your Amazon OTP. Do not share it with anyone."],
    "LINE": ["認証番号: {code} この番号をLINEアプリの画面で入力してください。番号の有効期限は30分です。"],
    "Yahoo": ["確認コード: {code} 上記の番号を画面へ入力してください。Yahoo! JAPAN"],
    "Instagram": ["Use {code} to verify your Instagram account."],
    "Netease": ["[{company}] {code} (NetEase Verification Code)"],
    "PayPal": ["PayPal: Your security code is {code}. It expires in 10 minutes. Don't share this code with anyone. PayPal will never call to ask for this code."],
    "HUAWEI": ["{code}:HUAWEI ID verification code. For your security, don't share this verification code with anyone else."],
    "Phone Code": ["Your verification code is {code}."],
    "Twitter": ["Your 6-digit Twitter confirmation code is {code}. @mobile.twitter.com #{code}"]
}

# Define a list to store the companies for which we have message templates
companies = ["FACEBOOK", "Amazon", "LINE", "Yahoo", "Instagram", "Netease", "PayPal", "HUAWEI", "Phone Code", "Twitter"]

# Define a function to generate a random message for a given company
def generate_mock_message(company):
    # Select a random message template for the company
    template = random.choice(message_templates[company])
    # Generate a random verification code
    code = str(random.randint(100000, 999999))
    # Replace the {code} placeholder in the message template with the generated code
    message = template.replace("{code}", code)
    # Replace the {account} placeholder in the message template with the name of the company
    message = message.replace("{account}", company)
    # Replace the {company} placeholder in the message template with the name of the company
    message = message.replace("{company}", company)
    return message

# Generate a random message for a random company

timeago = 50

for i in range(10):
    company = random.choice(companies)
    message = generate_mock_message(company)
    timeago = timeago - random.randint(1,5)
    print(f"{company}:{message} {timeago} minutes ago")


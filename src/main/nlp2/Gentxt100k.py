# 作者：陶文
# 链接：https://zhuanlan.zhihu.com/p/628865715
# 来源：知乎
# 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

import urllib.request
import json
import pprint

file_contents = ''
with open('test.txt', 'r') as f:
    file_contents = f.read()

data = json.dumps({
    "prompt": "\n\nHuman: 在下面这个故事中有哪几个主要人物，他们之间的关系是什么样的？\n" + file_contents + "\n在上述故事中有哪几个主要人物，他们之间的关系是什么样的？\n\nAssistant: ",
    "model": "claude-v1.3-100k", 
    "max_tokens_to_sample": 8000, 
    "stop_sequences": ["\n\nHuman:"]
}).encode('utf-8')
headers = {'Content-Type': 'application/json',
"x-api-key": "xxx"
}
request = urllib.request.Request('https://api.anthropic.com/v1/complete', data=data, headers=headers, method='POST')
response = urllib.request.urlopen(request)

pprint.pprint(json.loads(response.read().decode('utf-8')))

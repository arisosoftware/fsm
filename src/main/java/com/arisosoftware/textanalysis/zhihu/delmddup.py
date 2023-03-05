# -*- coding: utf-8 -*-
import io
import re
import sys
# Requirement: 
  

simpleskip = {

"收藏",

"喜欢",

"收起",

"关注问题写回答",

"邀请回答",

"被浏览",

"分享",

"真诚赞赏，手留余香",

"赞赏",

"还没有人赞赏，快来当第一个赞赏的人吧！",

"关注者",

"评论千万条，友善第一条",

"收起评论",

"回复",

"赞",

"选择语言",

"刘看山知乎指南知乎协议知乎隐私保护指引",

"应用工作申请开通知乎机构号",

"侵权举报网上有害信息举报专区",

"京 ICP 证 110745 号",

"京 ICP 备 13052560 号 - 1",

"京公网安备 11010802020088 号",

"京网文[2022]2674-081 号",

"药品医疗器械网络信息服务备案",

"（京）网药械信息备字（2022）第00334号服务热线：400-919-0001违法和不良信息举报：010-82716601举报邮箱：jubao@zhihu.com",

"儿童色情信息举报专区",

"互联网算法推荐举报专区",

"养老诈骗举报专区",

"MCN 举报专区",

"信息安全漏洞反馈专区",

"内容从业人员违法违规行为举报",

"网络谣言信息举报入口",

"证照中心Investor Relations",

"联系我们 © 2023 知乎",

"北京智者天下科技有限公司版权所有",

"本站提供适老化无障碍服务",

"首页",

"知学堂",

"会员",

"发现",

"等你来答",

"提问",

"消息",

"私信",

"创作中心",

"收起评论",

"关注问题写回答",

"邀请回答",

"评论千万条，友善第一条"

}
# Define a translation table that maps the characters to be removed to None
patterns = {
    r"\u200B":"",
    r"\x0b":  "",  # replace to null for remove it.
    r"\d+,\d+,\d*":  "",  # replace to null for remove it.
    r"他们也关注了该问题.*":  "",  # replace to null for remove it.
    r"​好问题 \d+": "",  # replace to null for remove it.
    r"\d+ 条评论": "",  # replace to null for remove it.
    r"查看全部 \d+ 个回答": "",  # replace to null for remove it.
    r"赞同 \d+": "",  # replace to null for remove it.
    r"赞同 \d+$": "",  # replace to null for remove it.
    r"查看全部 \d+ 条回复​": "",  # replace to null for remove it.
    r"展开其他 \d+ 条回复​$": "",  # replace to null for remove it.
    r".*等 \d+ 人赞同了该回答$": "",  # replace to null for remove it.
    r"点击打开.*的主页" : "",
}


#Main route


if len(sys.argv) < 3:
    print("Usage: python script.py input_file output_file")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

with open(input_file, 'r', encoding='utf8') as f:
    text = f.read()

lines = text.split("\n")
seen_lines = {}
tline = []
output_lines = []

for sline in lines:

    if sline is None or len(sline.strip()) == 0:
        continue

    for pattern, substitution in patterns.items():
        sline2 = re.sub(pattern, substitution, sline)
        if sline2 != sline :
            #print(sline)
            sline = sline2
    
    if sline is None or len(sline.strip()) == 0:
        continue

    if sline in simpleskip:
        print(sline)
        continue

    tline.append(sline)


previous_line = None
for i in range(len(tline)):
    line = tline[i]
    previous_line = tline[i-1] if i > 0 else None
    if (line == previous_line):
        continue

    if line in seen_lines and (previous_line in seen_lines):
        prev_seen_i = seen_lines[line]
        prev_seen_ix = seen_lines[previous_line]
        if (prev_seen_i == (prev_seen_ix + 1)):
            continue
    seen_lines[line] = i
    output_lines.append(line)
    
    
with open(output_file, 'w', encoding='utf8') as f:
    f.write('\n'.join(output_lines))

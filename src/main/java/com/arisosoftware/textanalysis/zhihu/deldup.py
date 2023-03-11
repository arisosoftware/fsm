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
    "关注",
    "推荐",
    "热榜",
    "视频",
    "搜索你感兴趣的内容…",
    "评论发布成功！",
    "添加评论",
#    "…阅读全文$" 
    "默认",
    "最新",
    "作者",
    "赞同",
    " 举报",
    "点击查看全部评论",
    "去咨询",
    "赞赏用户",
    "默认",
    "我的收藏",

}

# Define a translation table that maps the characters to be removed to None
patterns = {
    r"\u200B":"",
    r"\x0b":  "",  # replace to null for remove it.
    r"\d+,\d+,\d*":  "",  # replace to null for remove it.
    r"他们也关注了该问题.*":  "",  # replace to null for remove it.
    r"​好问题 \d+": "",  # replace to null for remove it.
    r"\d+ 条评论": "",  # replace to null for remove it.
    r"\d 人已赞赏$" :"",
    r"​已赞同 \d+": "",  # replace to null for remove it.
    r"^赞同 \d+": "",  # replace to null for remove it.
    r"查看全部 \d* 条回复" : "",
    r"查看全部 \d+ 个回答": "",  # replace to null for remove it.
    r"^展开其他 .* 条回复$":"",
    r".*等 .*赞同了该回答$": "___",  # replace to null for remove it.
    r".*等 .*赞同了该文章": "",
    r".*赞同了回答.*前":"",
    r".*赞同了文章.*前":"",
    r"\d 人赞同了该回答":"",
    r"点击打开.*的主页" : "",
    r"^\d+$" :"",
    r".* · IP 属地.*" : "",
    r".*发布于 .*$" : "___",
    r"微信公众号.*" :"",
    r".默认." :"",
    r"还有 .+ 的动态被收起":"",
    r".*次咨询$" :"",
    r".*次赞同$" :"",
    r".*优秀回答者$" :"",
    r"\d+ 赞同 · \d+ 评论回答$" :"",
}


#Main route


if len(sys.argv) < 3:
    print("Usage: python script.py input_file output_file")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

with open(input_file, 'r', encoding='utf8') as f:
    text = f.read()
    #lines = [line.decode('utf-8').strip() for line in f.readlines()]
lines = text.split("\n")
seen_lines = {}
tline = []
output_lines = []
lineId = 0
for sline in lines:
    lineId = lineId + 1
    if sline is None or len(sline.strip()) == 0:
        continue

    for pattern, substitution in patterns.items():
        sline2 = re.sub(pattern, substitution, sline)
        if sline2 != sline :
            #print(sline)
            sline = sline2
    
    #if sline is None or len(sline.strip()) == 0:
    #    continue

    if sline in simpleskip:
        print(f"Line {lineId} removed: {sline}" )
        continue

    if (sline == '海盐计划') | (sline == "相关问题") :
        break

    tline.append(sline)



previous_line = None
textlenth = len(tline)
for i in range(textlenth):
    line = tline[i]
    previous_line = tline[i-1] if i > 0 else None
    next_line = tline[i+1]  if i < textlenth-1 else None
    if (line == previous_line):
        continue

    if line in seen_lines:
        prev_seen_i = seen_lines[line]
        
        if(previous_line in seen_lines):
            prev_seen_ix = seen_lines[previous_line]
            if (prev_seen_i == (prev_seen_ix + 1)):
                #output_lines.append("\n")
                continue

        if(next_line in seen_lines):
            prev_seen_iv = seen_lines[next_line]
            if (prev_seen_i == (prev_seen_iv - 1)):
                #output_lines.append("\n")
                continue

    seen_lines[line] = i
    if line != "___" :
        output_lines.append(line)
    else:
        output_lines.append("\t")
    
with open(output_file, 'w', encoding='utf8') as f:
    f.write('\n'.join(output_lines))

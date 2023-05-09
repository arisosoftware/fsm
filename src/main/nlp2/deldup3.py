# -*- coding: utf-8 -*-
import io
import re
import sys
import os
from collections import OrderedDict
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
    "关注的话题",
    "关注的话题",
    "关注的专栏",
    "关注的问题",
    "关注的收藏夹",
    "屏蔽用户举报用户",
    "​申请转载",
    "首发于",
    "写文章",
    "关注他",
    "还没有评论，发表第一个评论吧",
    "推荐阅读","申请转载","已关注","文章被以下专栏收录",
    "广告",
    "相关问题",
    "相关推荐",
    "live",
    "阅读",

    "动态",
    "关注",
    "关注他发私信",
    " 查看详细资料关注他发私信",
    "他的回答",
    "按时间排序",
    "点击可播放视频",

}

# Define a translation table that maps the characters to be removed to None
patterns = {
    r"\u200B":"",
    r"\x0b":  "",  # replace to null for remove it.
    #r"[\d,]*":  "",  # replace to null for remove it.
    r"他们也关注了该问题.*":  "",  # replace to null for remove it.
    r"​好问题 \d+": "",  # replace to null for remove it.
    r"[\d,]+ 条评论": "",  # replace to null for remove it.
    r"[\d,]+ 人已赞赏$" :"",

    r"^赞同.[\d,]+": "",  # replace to null for remove it.
    r"查看全部.[\d,]* 条回复" : "",
    r"查看全部.[\d,]+ 个回答": "",  # replace to null for remove it.
    r"^展开其他 [\d,]* 条回复$":"",
    r".*等 .*赞同了该回答$": "___",  # replace to null for remove it.
    r".*等 .*赞同了该文章": "",
    r".*赞同了回答.*前":"",
    r".*赞同了文章.*前":"",
    r"[\d,]+ 人也赞同了该回答":"",
    r"[\d,]+ 人赞同了该回答":"",
    r"点击打开.*的主页" : "",
    r"^[\d,]+$" :"",
    r".* · IP 属地.*" : "",
    r".*发布于 .*$" : "___",
    r"微信公众号.*" :"",
    r".默认." :"",
    r"还有 .+ 的动态被收起":"",
    r".*次咨询$" :"",
    r".*次赞同$" :"",
    r".*优秀回答者$" :"",
    r"[“【‘]" :"「",
    r"[”】’]" :"」",
    r"[\d,]+ 赞同 · [\d,]+ 评论回答$" :"",
    r"[\d,]+ 个回答被折叠（为什么？）":"",
    r"[\d,]+ 人读过":"",
    r".*举报专区$" :"",
    r".*的优秀答主$" :"",
    r"回答[\d,]+" :"",
    r"^视频[\d,]+" :"",
    r"^提问[\d,]+" :"",
    r"^回答[\d,]+" :"",
    r"^文章[\d,]+" :"",
    r"^专栏[\d,]+" :"",
    r"^想法[\d,]+" :"",
    r"^收藏[\d,]+" :"",
    r"^用户封面IP 属地.*" :"",
    r"[\d,]+ 人赞同了该文章$" :"",
    r"[0-9.,]+ 万播放" :"",
    r"[\d,]+ 赞同视频" :"",
    r".*发表了文章.*\d+ 分钟前" :"",
    r"已赞同 [\d,]+" : "",

} 


def removeDuplation(inputFileName, outputFileName):
    print(inputFileName + " inputFileName")
    print(outputFileName + " outputFileName")
    input_file = inputFileName
    output_file = outputFileName

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
        
        #if sline is None or len(sline) == 0:
        if sline is None:
            continue

        sline = sline.strip()

        pIndex = 0

        for pattern, substitution in patterns.items():
            pIndex = pIndex + 1
            sline2 = re.sub(pattern, substitution, sline)
            if sline2 != sline :
                if pIndex>3:
                    print(f"Line {lineId} Regex:{pattern}|{substitution}|{sline},=> {sline2}" )

                sline = sline2

        
        if sline in simpleskip:
            print(f"Line {lineId} removed: {sline}" )
            continue

        #if (sline == '海盐计划') | (sline == "相关问题") :
        #    break

        tline.append(sline)


    # create an ordered dictionary to store unique lines in the order they appear
    unique_lines = OrderedDict()

    previous_line = None
    tline_lenth = len(tline)
    
    

    for i in range(tline_lenth):
        line = tline[i]
        #print(f"Line {i} .. {line}" )

        previous_line = tline[i-1] if i > 0 else None
        
        next_line = tline[i+1]  if i < tline_lenth-1 else None
        
        if (line == previous_line):
            continue

        if len(line)>0 and line in unique_lines:
            continue

        #if (len(line) == 0) and (previous_line is not None ) and (len(previous_line)==0):
            #output_lines.append("keep $line")
            #continue

        # add the line to the dictionary
        unique_lines[line] = True

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
        #f.write('\n'.join(output_lines))
        tLength = len(output_lines)
        for i in range(tLength):
            line = output_lines[i]
            if i<tLength-1 :
                nline = output_lines[i+1]
                if line == "" and nline == "" :
                    continue
                f.write(line+'\n')

#######################################################

#######################################################

#Main route
prgname = sys.argv[0]

for theFile in sys.argv:
    if (theFile != prgname):
        print(theFile)
        outfilename_m = theFile + ".md"
        removeDuplation( theFile, outfilename_m)

# if len(sys.argv) > 1:
#     input_file = sys.argv[1]

# if len(sys.argv) > 2:
#     output_file = sys.argv[2]
# else:
#     output_file = input_file +".md"

# if len(sys.argv) < 2:
#     print("Usage: python script.py input_file output_file")
#     sys.exit(1)


# -*- coding: utf-8 -*-
import io
import re
import sys
import os
import difflib
from collections import OrderedDict
from datetime import datetime

# Requirement: 
#>>> import hashlib
#>>> hashlib.sha256(b"Nobody inspects the spammish repetition").hexdigest()
   

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

    "书店",
    "圆桌",
    "专栏",
    "付费咨询",
    "百科",
    "我关注的问题",
    "我的邀请",
    "我的余额",
    "我的礼券",
    "站务中心",
    "帮助与客服",
    "版权服务中心",
    "回答问题",
    "发视频",
    "写想法",
    "恭喜已加入海盐计划",
    "加入海盐计划可加速成长获得更多扶持。",
    "今日阅读 (播放) 数",
    "昨日数据",
    "今日新增赞同数",
    "「馆」中窥万物",
    "此生必看的神仙文物",
    "塞尔达传说 | 海拉鲁一千零一夜",
    "点击参与讨论 >>",
    "第三届科幻文学奖「读客 X 知乎联合征文」",
    "2023 季中冠军赛",
    "关注圆桌参与精彩赛事热聊",
    "未来生活家",
    "邀你分享家的智能与舒适",
    "进入创作中心",
    "推荐关注",
    "记得点赞收藏哦！"

}

# Define a translation table that maps the characters to be removed to None
# this patterns for each line
patterns = {

    #r"[\d,]*":  "",  # replace to null for remove it.
    r"他们也关注了该问题.*":  "",  # replace to null for remove it.
    r"​好问题 \d+": "",  # replace to null for remove it.

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
    r".*等 .*赞同了该回答$": "___",  # replace to null for remove it.
    r"^用户封面IP 属地.*" :"",
    r"[\d,]+ 人赞同了该文章$" :"",
    r"[0-9.,]+ 万播放" :"",
    r"[\d,]+ 赞同视频" :"",
    r".*发表了文章.*\d+ 分钟前" :"",
    r"已赞同 [\d,]+" : "",
    r"回复了回答下你的评论.* " : "",
    r".*回答了问题.*\d+ 分钟前" :"",
    r".*的人也关注了.*TA" :"",
    r".*网药械信息备字.*" :"",
    r"你关注的.*也关注了.*TA" :"",
} 

# this patterns for full text file ,

regex_FullFile = [
    r"\u200B",
    r"\x0b",
    r"你可能感兴趣.*.*领域答主\n",
    r".*\n.*阅读全文\n",
    r".*\n.*领域答主\n",
    r"<\/?p>",
    r"\n查看全部.[\d,]* 条回复",
    r"\n[\d,]+ 条评论",
    r"\n[\d,]+ 人已赞赏$",
    r"\n赞同.[\d,]+",    
    r"\n查看全部.[\d,]* 条回复",
    r"\n查看全部.[\d,]+ 个回答",
    r"\n展开其他 [\d,]* 条回复$",
    r"\n.*等 .*赞同了该文章",
    r"\n.*赞同了回答.*前",
    r"\n.*赞同了文章.*前",
    r"\n回答[\d,]+",
    r"\n视频[\d,]+",
    r"\n提问[\d,]+",
    r"\n回答[\d,]+",
    r"\n文章[\d,]+",
    r"\n专栏[\d,]+",
    r"\n想法[\d,]+",
    r"\n收藏[\d,]+",
]


def apply_regex_replace(regex_list, input_file_path, output_file_path):
    # Read the input file
    with open(input_file_path, "r") as file:
        input_text = file.read()

    # Apply regular expressions
    for regex in regex_list:
        input_text = re.sub(regex, "", input_text)

    # Write the modified text to the output file
    with open(output_file_path, "w") as file:
        file.write(input_text)



# #the Python function that takes file1, file2, and file3 as parameters. It performs the regex replacement in file1.txt, saves the modified content to file2.txt, compares the two files, and saves the differing content to file3.txt

# def regex_replace_and_compare(file1, file2):
#     # Perform regex replacement in file1.txt and save to file2.txt
#     with open(file1, "r") as f1, open(file2, "w") as f2:
#         content = f1.read()
#         modified_content = re.sub(r".*\n.*阅读全文", "", content)
#         f2.write(modified_content)

#     # Compare file1.txt and file2.txt and save differences to file3.txt
#     with open(file1, "r") as f1, open(file2, "r") as f2:
#         diff_output = list(difflib.unified_diff(f1.readlines(), f2.readlines(), lineterm=""))
#         diff_output = [line[1:] for line in diff_output if line.startswith("+")]
#         #f3.write("".join(diff_output))
#         print("".join(diff_output))


# create an ordered dictionary to store unique lines in the order they appear -- update to global
unique_lines = OrderedDict()

def reverseFile(inputFileName, outputFileName):
    # Read the input file
    with open(inputFileName, "r") as file:
        lines = file.readlines()

    # Reverse the lines
    reversed_lines = lines[::-1]

    # Write the reversed lines to the output file
    with open(outputFileName, "w") as file:
        file.writelines(reversed_lines)

def update_unique_lines(input_file):
    with open(input_file, 'r', encoding='utf8') as f:
        text = f.read()
        
    lines = text.split("\n")
    for sline in lines:
        if len(sline)>0 and sline in unique_lines:
            continue
        # add the line to the dictionary
        unique_lines[sline] = True




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

        sline = sline.replace("\r","").replace("\n","").strip()

        pIndex = 0

        for pattern, substitution in patterns.items():
            pIndex = pIndex + 1
            sline2 = re.sub(pattern, substitution, sline)
            if sline2 != sline :
                #debug
                #if pIndex>3:
                #    print(f"Line {lineId} Regex:{pattern}|{substitution}|{sline},=> {sline2}" )
                sline = sline2
            # end //if sline2 != sline :
        
        if sline in simpleskip:
            print(f"Line {lineId} removed: {sline}" )
            continue

        #if (sline == '海盐计划') | (sline == "相关问题") :
        #    break

        tline.append(sline)


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

        unique_lines[line] = True

        if line in seen_lines:
            prev_seen_i = seen_lines[line]

            if(previous_line in seen_lines):
                prev_seen_ix = seen_lines[previous_line]
                if (prev_seen_i == (prev_seen_ix + 1)):
                    continue

            if(next_line in seen_lines):
                prev_seen_iv = seen_lines[next_line]
                if (prev_seen_i == (prev_seen_iv - 1)):
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

        if theFile == "filter":
            update_unique_lines(theFile)
            continue

        tmpfile1 = "tmpreverse.1"
        tmpfile2 = "tmpreverse.2"
        tmpfile3 = "tmpreverse.3"
        reverseFile(theFile, tmpfile1)
        apply_regex_replace(regex_FullFile, tmpfile1, tmpfile2)
        removeDuplation( tmpfile2, tmpfile3)
        outfilename_m = theFile + datetime.now().strftime("_%Y%m%d_%H%M")+ ".md"
        reverseFile(tmpfile3, outfilename_m)

        os.remove(tmpfile1)
        os.remove(tmpfile2)
        os.remove(tmpfile3)

# if len(sys.argv) > 1:
#     input_file = sys.argv[1]

# if len(sys.argv) > 2:
#     output_file = sys.argv[2]
# else:
#     output_file = input_file +".md"

# if len(sys.argv) < 2:
#     print("Usage: python script.py input_file output_file")
#     sys.exit(1)


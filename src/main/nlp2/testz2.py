# -*- coding: utf-8 -*-
import io
import re
import sys
import os
import difflib
from collections import OrderedDict
from datetime import datetime


def add_blank_lines(article, character_keywords):
    lines = article.split('\n')
    result = []
    current_character = None

    for line in lines:
        for keyword in character_keywords:
            if keyword in line:
                if current_character != keyword:
                    result.append('\n')  # 添加空行
                    current_character = keyword
                break
        result.append(line + '\n')

    return ''.join(result)


# Function to process a line and adjust index
def process_line(line, index):
    # Loop through each character in the line
    new_line = ''
    for char in line:
        if char == '「':
            index += 1
            if index > 1:
                new_line += ' '
        elif char == '」':
            index -= 1
            if index > 1:
                new_line += ' '
        else:
            new_line += char
    return new_line, index



# 输入文章
article = """
这里宝玉忙忙的穿了衣裳出来，忽见林黛玉在前面慢慢的走着，似有拭泪之状，便忙赶上来，笑道：“妹妹往那里去？怎么又哭了？又是谁得罪了你？"林黛玉回头见是宝玉，便勉强笑道：“好好的，我何曾哭了。”宝玉笑道：“你瞧瞧，眼睛上的泪珠儿未干，还撒谎呢．"一面说，一面禁不住抬起手来替他拭泪．林黛玉忙向后退了几步，说道：“你又要死了！作什么这么动手动脚的！"宝玉笑道：“说话忘了情，不觉的动了手，也就顾不的死活．"林黛玉道：“你死了倒不值什么，只是丢下了什么金，又是什么麒麟，可怎么样呢？"一句话又把宝玉说急了，赶上来问道：“你还说这话，到底是咒我还是气我呢？"林黛玉见问，方想起前日的事来，遂自悔自己又说造次了，忙笑道：“你别着急，我原说错了．这有什么的，筋都暴起来，急的一脸汗。”一面说，一面禁不住近前伸手替他拭面上的汗．宝玉瞅了半天，方说道"你放心"三个字．林黛玉听了，怔了半天，方说道：“我有什么不放心的？我不明白这话．你倒说说怎么放心不放心？"宝玉叹了一口气，问道：“你果不明白这话？难道我素日在你身上的心都用错了？连你的意思
若体贴不着，就难怪你天天为我生气了。”林黛玉道：“果然我不明白放心不放心的话。”宝玉点头叹道：“好妹妹，你别哄我．果然不明白这话，不但我素日之意白用了，且连你素日待我之意也都辜负了．你皆因总是不放心的原故，才弄了一身病．但凡宽慰些，这病也不得一日重似一日。”林黛玉听了这话，如轰雷掣电，细细思之，竟比自己肺腑中掏出来的还觉恳切，竟有万句言语，满心要说，只是半个字也不能吐，却怔怔的望着他．此时宝玉心中也有万句言语，不知从那一句上说起，却也怔怔的望着黛玉．两个人怔了半天，林黛玉只咳了一声，两眼不觉滚下泪来，回身便要走．宝玉忙上前拉住，说道：“好妹妹，且略站住，我说一句话再走。”林黛玉一面拭泪，一面将手推开，说道：“有什么可说的．你的话我早知道了！"口里说着，却头也不回竟去了．
"""

# 角色名字关键词
character_keywords = ["宝玉", "林黛玉"]

# 添加空行并输出结果
result = add_blank_lines(article, character_keywords)
print(result)



# # Read the input file
# with open('fonts.txt', 'r') as file:
#     index = 0
#     for line in file:
#         processed_line, index = process_line(line, index)
#         print(processed_line, end='')

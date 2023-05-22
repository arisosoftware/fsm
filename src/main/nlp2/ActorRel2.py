import networkx as nx
import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties

font_path = '/usr/share/fonts/opentype/noto/NotoSansCJK-Regular.ttc'
font_prop = FontProperties(fname=font_path)

plt.rcParams['font.family'] = font_prop.get_name()
plt.rcParams['font.sans-serif'] = [font_prop.get_name()]

# 创建图形
G = nx.Graph()
宋婷 姜棉 情敌 


宋亮是一个高官

宋婷是一位年轻漂亮的女孩
王诗音 个很有才华的人年轻漂亮的女孩,音乐很好.
姜棉  优雅地坐在上面，穿着一件黑色紧身连衣裙，完美地勾勒出她曼妙的身材,年轻漂亮的女孩

张艺。张艺是个很有才华的人，他的画作在艺术圈内备受赞誉。

在阳光明媚的早晨 环境描写

他们站在一条人烟稀少的街道上，路灯微弱的光芒照亮了他们的面庞。街上的风微微吹动着宋烟的长发，她的香气随风而散。在这个寂静的夜晚，他们仿佛是整个世界中唯一的两个人。

顺序必须这样:环境描写,内心独白,详细地描述宋烟和王颖的感受和动作以便读者更好地体验这个场景


宋烟
宋烟是一个典型的职场女性，外表优雅大方，性格坚韧自信。她的长发披肩，眼神犀利，让人不敢小觑。她总是以一种冷静的态度处理问题，给人以稳重可靠的感觉。她喜欢简单、清新的打扮，穿着得体，给人以亲和力。虽然在工作中她会表现得强势一些，但在平常生活中，她是一个容易相处的人，喜欢与人交流，乐于助人。

王颖
王颖是一个高大帅气的男孩子，长相俊美，轮廓分明。他的眼睛深邃明亮，透露出一种坚定果敢的气息。他的身材高大健硕，肌肉线条分明，给人以强大的力量感。在穿着方面，他喜欢简约又时尚的搭配，经常穿着白衬衫和黑色西装外套。他的性格独立坚强，充满自信和决心，从不会轻易放弃自己的目标和理想。同时，他也有一颗温柔善良的心，对待身边的人总是充满关爱和耐心。




# 添加节点
nodes = ['张柏芝', '谢霆锋', '王菲', '李亚鹏', '瞿颖', '张亚东', '窦唯', '周迅', '窦鹏', '窦颖']
G.add_nodes_from(nodes)

# 添加边和标签
edges = [('张柏芝', '谢霆锋', {'relation': '前夫'}),
         ('谢霆锋', '王菲', {'relation': '前女友'}),
         ('谢霆锋', '瞿颖', {'relation': '前男友'}),
         ('王菲', '李亚鹏', {'relation': '前夫'}),
         ('王菲', '窦唯', {'relation': '前夫'}),
         ('李亚鹏', '瞿颖', {'relation': '前男友'}),
         ('李亚鹏', '周迅', {'relation': '前女友'}),
         ('瞿颖', '张亚东', {'relation': '前男友'}),
         ('张亚东', '窦颖', {'relation': '前夫'}),
         ('周迅', '窦鹏', {'relation': '前男友'}),
         ('窦唯', '窦鹏', {'relation': '堂兄'}),
         ('窦颖', '窦鹏', {'relation': '兄妹关系'})]
G.add_edges_from(edges)

# 绘制图形
pos = nx.spring_layout(G)
nx.draw(G, pos, with_labels=True)

# 添加边标签
edge_labels = {(u, v): d['relation'] for u, v, d in G.edges(data=True)}
nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_size=8)
plt.savefig('relationship.png')
plt.show()




# 解决负号显示问题
plt.rcParams['axes.unicode_minus'] = False
 
# # 创建一个有向图
# G = nx.DiGraph()

# # 添加节点
# nodes = ['张柏芝', '谢霆锋', '王菲', '李亚鹏', '瞿颖', '张亚东', '窦唯', '周迅', '窦鹏', '窦颖']
# G.add_nodes_from(nodes)

# # 添加边
# edges = [('谢霆锋', '张柏芝'), ('王菲', '谢霆锋'), ('瞿颖', '李亚鹏'), ('周迅', '朴树'),
#          ('李亚鹏', '王菲'), ('张亚东', '瞿颖'), ('窦唯', '王菲'), ('窦鹏', '周迅'),
#          ('窦颖', '张亚东'), ('张亚东', '瞿颖'), ('周迅', '李亚鹏')]
# G.add_edges_from(edges)

# # 绘制图形
# pos = nx.circular_layout(G) # 设定节点的布局
# #pos = nx.spring_layout(G)
# nx.draw_networkx_nodes(G, pos, node_size=500)
# nx.draw_networkx_labels(G, pos, font_size=12 )
# nx.draw_networkx_edges(G, pos, width=1.0, alpha=0.5, arrowsize=10)
# plt.axis('off') # 关闭坐标轴
# plt.show() # 展示图形

# # 创建图形
# G = nx.Graph()

# # 添加节点
# nodes = ['张柏芝', '谢霆锋', '王菲', '李亚鹏', '瞿颖', '张亚东', '窦唯', '周迅', '窦鹏', '窦颖']
# G.add_nodes_from(nodes)

# # 添加边和标签
# edges = [('张柏芝', '谢霆锋', '前夫'), ('谢霆锋', '王菲', '前女友'), ('谢霆锋', '瞿颖', '前男友'), ('王菲', '李亚鹏', '前夫'), ('王菲', '窦唯', '前夫'), ('李亚鹏', '瞿颖', '前男友'), ('李亚鹏', '周迅', '前女友'), ('瞿颖', '张亚东', '前男友'), ('张亚东', '窦颖', '前夫'), ('周迅', '窦鹏', '前男友'), ('窦唯', '窦鹏', '堂兄'), ('窦颖', '窦鹏', '兄妹关系')]
# G.add_edges_from(edges)

# # 绘制图形
# pos = nx.spring_layout(G)
# nx.draw(G, pos, with_labels=True)

# # 添加边标签
# edge_labels = {(u, v): d for u, v, d in G.edges(data=True)}
# nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_size=8)

# plt.show()


#在上面的代码中，每条边都是一个包含两个节点名称和一个字典的元组。字典中包含边的标签信息。然后我们使用add_edges_from方法将边添加到图中，并使用draw_networkx_edge_labels函数为边添加标签。





#你见过的最乱的男女关系是怎么样的？ - 独自莫凭栏的回答 - 知乎
#https://www.zhihu.com/question/549723914/answer/3018653230

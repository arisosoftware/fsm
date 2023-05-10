import networkx as nx
import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties

font_path = '/usr/share/fonts/opentype/noto/NotoSansCJK-Regular.ttc'
font_prop = FontProperties(fname=font_path)

plt.rcParams['font.family'] = font_prop.get_name()
plt.rcParams['font.sans-serif'] = [font_prop.get_name()]

# 创建图形
G = nx.Graph()

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

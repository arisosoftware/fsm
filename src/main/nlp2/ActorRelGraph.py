import networkx as nx
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
 
# 设置中文字体
plt.rcParams['font.sans-serif'] = ['Noto Sans CJK SC Black', 'Noto Sans CJK TC Black', 'Noto Sans CJK JP Black', 'Noto Sans CJK KR Black']

# 解决负号显示问题
plt.rcParams['axes.unicode_minus'] = False
 


# 获取所有已安装的字体
#fonts = fm.findSystemFonts()

# 输出所有字体的名称和路径
#for font in fonts:
#   print(font)

# 创建一个有向图
G = nx.DiGraph()

# 添加节点
nodes = ['张柏芝', '谢霆锋', '王菲', '李亚鹏', '瞿颖', '张亚东', '窦唯', '周迅', '窦鹏', '窦颖']
G.add_nodes_from(nodes)

# 添加边
edges = [('谢霆锋', '张柏芝'), ('王菲', '谢霆锋'), ('瞿颖', '李亚鹏'), ('周迅', '朴树'),
         ('李亚鹏', '王菲'), ('张亚东', '瞿颖'), ('窦唯', '王菲'), ('窦鹏', '周迅'),
         ('窦颖', '张亚东'), ('张亚东', '瞿颖'), ('周迅', '李亚鹏')]
G.add_edges_from(edges)

# 绘制图形
pos = nx.spring_layout(G) # 设定节点的布局
nx.draw_networkx_nodes(G, pos, node_size=500)
nx.draw_networkx_labels(G, pos, font_size=12, font_family='Noto Sans CJK SC Black')
nx.draw_networkx_edges(G, pos, width=1.0, alpha=0.5, arrowsize=10)
plt.axis('off') # 关闭坐标轴
plt.show() # 展示图形

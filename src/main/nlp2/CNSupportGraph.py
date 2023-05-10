import networkx as nx
import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties

font_path = '/usr/share/fonts/opentype/noto/NotoSansCJK-Regular.ttc'
font_prop = FontProperties(fname=font_path)

plt.rcParams['font.family'] = font_prop.get_name()
plt.rcParams['font.sans-serif'] = [font_prop.get_name()]

# 创建一个有标签的图形
G = nx.DiGraph()
G.add_edge('张三', '李四')
G.add_edge('张三', '王五')
G.add_edge('王五', '张三')
G.add_edge('李四', '张三')
G.add_edge('李四', '王五')
G.add_edge('王五', '李四')
# 指定字体和编码方式

# 绘制图形并显示标签
nx.draw_networkx(G, labels={n: n for n in G.nodes()}, with_labels=True, font_family='Noto Sans CJK')
plt.title(u'显示中文')
# 显示图形
plt.show()


#pe/lib/python3.7/tkinter/__init__.py:749: UserWarning: Glyph 24352 (\N{CJK UNIFIED IDEOGRAPH-5F20}) missing from current font.




# 获取所有已安装的字体
#fonts = fm.findSystemFonts()

# 输出所有字体的名称和路径
#for font in fonts:
#   print(font)



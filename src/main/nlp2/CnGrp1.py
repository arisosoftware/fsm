import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties

font_path = '/usr/share/fonts/opentype/noto/NotoSansCJK-Regular.ttc'
font_prop = FontProperties(fname=font_path)

plt.rcParams['font.family'] = font_prop.get_name()
plt.rcParams['font.sans-serif'] = [font_prop.get_name()]

plt.plot([1, 2, 3], [4, 5, 6])
plt.xlabel('横轴', fontproperties=font_prop)
plt.ylabel('纵轴', fontproperties=font_prop)
plt.title('示例图', fontproperties=font_prop)
plt.show()

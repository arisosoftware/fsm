import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# 定义常量
D = 2.3e-9  # 扩散系数
Q = 10000   # 排放量，单位：吨
A = 10      # 排放点宽度，单位：米
L = 500000  # 海岸线宽度，单位：米
H = 18      # 水深，单位：米
C1 = 0.07   # 初始盐水浓度
C2 = 0.035  # 海水盐水浓度

# 定义空间范围
nx = 500   # x轴方向网格数
ny = 1000  # y轴方向网格数
dx = L / nx
dy = H / ny

# 定义时间参数
nt = 100   # 时间步数
dt = 86400 # 时间步长，单位：秒

# 初始化盐水浓度矩阵
C = np.zeros((ny, nx))

# 在排放点处添加盐水
C[:, :int(A/dx)] = C1 * Q / (dx * dy * H)

# 定义扩散方程
def diffuse(C):
    Cx = np.zeros_like(C)
    Cy = np.zeros_like(C)
    Cx[:, 1:-1] = (C[:, :-2] - 2 * C[:, 1:-1] + C[:, 2:]) / dx ** 2
    Cy[1:-1, :] = (C[:-2, :] - 2 * C[1:-1, :] + C[2:, :]) / dy ** 2
    return D * dt * (Cx + Cy)

# 创建动态图
fig, ax = plt.subplots()
im = ax.imshow(C, cmap='plasma', origin='lower', extent=[0, L/1000, 0, H])
ax.set_xlabel('Distance (km)')
ax.set_ylabel('Depth (m)')
ax.set_title('Salt Water Diffusion')
fig.colorbar(im, label='Concentration')

def update(frame):
    for i in range(nt):
        C += diffuse(C)
    im.set_data(C)
    return im,

ani = FuncAnimation(fig, update, frames=range(nt), interval=100)
plt.show()

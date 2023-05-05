import matplotlib.pyplot as plt

# 初始人口数
P = [1000000]

# 出生率和死亡率
b = 0.01
d = 1/(75-20)

# 夫妻生育孩子的概率
p1 = 0.52
p2 = 0.48

# 人口结婚和生育孩子的概率
p_married = 0.9
p_fertile = 0.75

# 迭代次数
T = 300

# 迭代计算人口变化
for t in range(T):
    P.append(P[t] + (0.52*b*P[t] - d*P[t]) * 0.9 + (0.48*b*P[t] - d*P[t]) * 0.75 * 0.9 * 0.15)

# 绘制人口增长曲线
plt.plot(P)
plt.xlabel('Year')
plt.ylabel('Population')
plt.title('Population Growth Model with Fertility and Mortality Rates')
plt.show()

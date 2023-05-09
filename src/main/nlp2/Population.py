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
T = 200

# 迭代计算每年的人口数量
for t in range(T):
    # 计算出生和死亡人口数量
    birth = (P[-1] * b * p_married * p_fertile * p1) + (P[-1] * b * p_married * p_fertile * p2 * 0.5)
    death = P[-1] * d
    # 更新人口数量
    p_new = P[-1] + (birth - death) * 0.9
    # 将新的人口数量添加到列表中
    P.append(p_new)

# 绘制人口增长曲线
plt.plot(P)
plt.xlabel('Year')
plt.ylabel('Population')
plt.title('Population Growth Model with Fertility and Mortality Rates (Iteration)')
plt.show()

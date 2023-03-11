
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import poisson


# This code generates a graph that shows the three Poisson distributions: one with a mean of m1 and an x offset of x_offset, one with a mean of m2 and a y offset of y_offset, and one that is the sum of the first two with no offset. The legend on the graph indicates which line corresponds to which Poisson distribution.




# Set the means and offsets
m1 = 8
m2 = 11
x_offset = 2
y_offset = 3

# Calculate the parameters lambda for the two Poisson distributions
lambda1 = m1 + x_offset
lambda2 = m2 + y_offset

# Generate a range of x values for the plot
x = np.arange(0, 20)

# Calculate the probabilities for each x value using the Poisson distribution
y1 = poisson.pmf(x, lambda1)
y2 = poisson.pmf(x, lambda2)

# Plot the two Poisson distributions on a graph
plt.plot(x, y1, label=f"Line 1: m1={m1}, x_offset={x_offset}")
plt.plot(x, y2, label=f"Line 2: m2={m2}, y_offset={y_offset}")

# Calculate the parameters lambda for the third summary line
lambda3 = m1 + m2 + x_offset + y_offset

# Calculate the probabilities for the third summary line using the Poisson distribution
y3 = poisson.pmf(x, lambda3)

# Plot the third summary line on the same graph
plt.plot(x, y3, label=f"Line 3: m1+m2={m1+m2}, x_offset={x_offset}, y_offset={y_offset}")

# Add a legend and axis labels
plt.legend()
plt.xlabel("Number of events")
plt.ylabel("Probability")

# Show the plot
plt.show()

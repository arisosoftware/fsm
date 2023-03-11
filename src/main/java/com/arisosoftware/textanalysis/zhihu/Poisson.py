


import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import poisson

# Set the means of the Poisson distributions
mu1 = 5
mu2 = 8
mu3 = 11

# Generate x-values
x = np.arange(0, 21)

# Compute the Poisson distribution probabilities for each x-value
probabilities1 = poisson.pmf(x, mu1)
probabilities2 = poisson.pmf(x, mu2)
probabilities3 = poisson.pmf(x, mu3)
probabilities_sum = probabilities1 + probabilities2 - probabilities3

# Plot the Poisson distribution lines
plt.plot(x, probabilities1, 'ro-', ms=8, label='Poisson (mu=5)')
plt.plot(x, probabilities2, 'bo-', ms=8, label='Poisson (mu=8)')
plt.plot(x, probabilities_sum, 'go-', ms=8, label='Sum')
plt.plot(x, probabilities3, 'yo-', ms=8, label='Poisson (mu=-11)')

# Add labels and a title
plt.xlabel('Number of events')
plt.ylabel('Probability')
plt.title('Poisson distributions with means of {} and {}'.format(mu1, mu2))

# Add a legend
plt.legend()

# Display the plot
plt.show()

import numpy as np
import matplotlib.pyplot as plt

a = 5 ** 5
print(a)

x = np.linspace(0.0001, 3, 100)
y = x ** x
plt.plot(x, y, 'ro-', mec='k', linewidth=3)
plt.show()
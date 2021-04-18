import matplotlib.pyplot as plt
import numpy as np


x = np.linspace(0,10,100)
y = []
up_limit = 0.8
for i in x:
    if i < 6:
        y.append(0)
    elif i < 9:
        y.append((i-6)/3 * up_limit)  
    else:
        y.append(up_limit)

plt.plot(x,y)
plt.show()
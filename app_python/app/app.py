from bokeh.plotting import figure, show
import sys
import json

data = json.load(sys.stdin)
y1 = data['new_cases']
y2 = data['cure_cases']
y3 = data['total_cases']

x = range(1, 481)

p1 = figure(title="Multiple line example", x_axis_label='x', y_axis_label='y')
p1.line(x, y1, legend_label="new cases", line_color="blue", line_width=2)
p1.line(x, y2, legend_label="total cases", line_color="red", line_width=2)
p1.line(x, y3, legend_label="cure cases", line_color="green", line_width=2)

show(p1)
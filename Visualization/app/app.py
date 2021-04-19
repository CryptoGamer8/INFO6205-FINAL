from bokeh.plotting import figure, show
from bokeh.layouts import row
import sys
import json

data = json.load(sys.stdin)

y1 = data['new_cases']
y2 = data['cure_cases']
y3 = data['total_cases']
y4 = data['mask_usage']
y5 = data['vaccine_avail']
cycles = data['cycles']
x = range(cycles)

p1 = figure(title="Multiple line example", x_axis_label='x', y_axis_label='y')
p1.line(x, y1, legend_label="new cases", line_color="blue", line_width=2)
p1.line(x, y2, legend_label="cure cases", line_color="red", line_width=2)
p1.line(x, y3, legend_label="total cases", line_color="green", line_width=2)


p2 = figure(title="Vaccine popularizing", x_axis_label='x', y_axis_label='y')
p2.line(x, y4, legend_label="mask usage", line_color="blue", line_width=2)
p2.line(x, y5, legend_label="vaccine injected ppl", line_color="red", line_width=2)
show(row(p1,p2))
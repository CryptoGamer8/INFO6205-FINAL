import json

test_dict = {'bigberg': [
    7600, {1: [['iPhone', 6300], ['Bike', 800], ['shirt', 300]]}]}

mask_usage = 0.5
vaccine_avail = 0.3

json_data = {
    "mask": mask_usage,

    ....mask_usage.mask_usage
    ...
}
json_str1 = json.dumps(json_data)

with open("./config/record.json", "w") as f:
    json.dump(json_str1,f)
    print("加载入文件完成...")


print(test_dict)
print(type(test_dict))
# dumps 将数据转换成字符串
json_str = json.dumps(test_dict)
print(json_str)
print(type(json_str))

execute_shell('run.sh')


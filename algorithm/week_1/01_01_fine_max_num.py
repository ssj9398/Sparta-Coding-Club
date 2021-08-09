## 가장큰수 구하기(1)
input = [3, 5, 6, 1, 2, 4]

def find_max_num(array):
    for num in array:
        for compare_num in array:
            if num<compare_num:
                break
        else:
            return num


result = find_max_num(input)
print(result)
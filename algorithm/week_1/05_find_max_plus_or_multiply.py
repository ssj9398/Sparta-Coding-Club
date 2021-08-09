# 왼쪽부터 오른쪽으로 + or * 하여 가장 큰 값을 만들기
input = [0, 3, 5, 6, 1, 2, 4]


def find_max_plus_or_multiply(array):
    multiply_sum = 0
    for number in array:
        if number <= 1 or multiply_sum <= 1:
            multiply_sum += number
        else:
            multiply_sum *= number
    return multiply_sum


result = find_max_plus_or_multiply(input)
print(result)
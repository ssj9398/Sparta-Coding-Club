# 소수 나열하기
input = 20


def find_prime_list_under_number(number):
    prime_list = []
    for number in range(2,number):
        for i in prime_list:
            if number % i == 0 and i*i<=number:
                break
        else:
            prime_list.append(number)
    return prime_list


result = find_prime_list_under_number(input)
print(result)
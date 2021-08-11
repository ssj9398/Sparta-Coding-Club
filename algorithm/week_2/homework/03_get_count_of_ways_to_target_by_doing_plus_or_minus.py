# 더하거나 빼서 타겟 구하기
numbers = [1, 1, 1, 1, 1]
target_number = 3
result_count = 0  # target 을 달성할 수 있는 모든 방법의 수를 담기 위한 변수


def get_count_of_ways_to_target_by_doing_plus_or_minus(array, target, current_index, current_sum):
    if current_index == len(array):
        if current_sum == target:
            global result_count
            result_count += 1
        return
    get_count_of_ways_to_target_by_doing_plus_or_minus(array, target, current_index + 1,
                                                       current_sum + array[current_index])
    get_count_of_ways_to_target_by_doing_plus_or_minus(array, target, current_index + 1,
                                                       current_sum - array[current_index])


get_count_of_ways_to_target_by_doing_plus_or_minus(numbers, target_number, 0, 0)
print(result_count)
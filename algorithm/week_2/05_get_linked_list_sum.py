# 두 링크드 리스트를 입력받았을 때, 합산한 값을 반환
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


class LinkedList:
    def __init__(self, value):
        self.head = Node(value)

    def append(self, value):
        cur = self.head
        while cur.next is not None:
            cur = cur.next
        cur.next = Node(value)


def get_linked_list_sum(linked_list_1, linked_list_2):
    linked_list_sum_sum_1 = _get_linked_list_sum(linked_list_1)
    linked_list_sum_sum_2 = _get_linked_list_sum(linked_list_2)
    print(linked_list_sum_sum_1, linked_list_sum_sum_2)
    return linked_list_sum_sum_1 + linked_list_sum_sum_2


def _get_linked_list_sum(linked_list):
    linked_list_sum = 0
    head = linked_list.head
    while head is not None:
        linked_list_sum = linked_list_sum * 10 + head.data
        head = head.next
    return linked_list_sum


linked_list_1 = LinkedList(6)
linked_list_1.append(7)
linked_list_1.append(8)

linked_list_2 = LinkedList(3)
linked_list_2.append(5)
linked_list_2.append(4)

print(get_linked_list_sum(linked_list_1, linked_list_2))

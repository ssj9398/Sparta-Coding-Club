# 링크드 리스트 끝에서 K 번째 값 출력하기
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

    def get_kth_node_from_last(self, k):
        node = self.head
        next_node = self.head

        for i in range(k):
            next_node = next_node.next

        while next_node is not None:
            node = node.next
            next_node = next_node.next

        return node


linked_list = LinkedList(6)
linked_list.append(7)
linked_list.append(8)

print(linked_list.get_kth_node_from_last(2).data)  # 7이 나와야 합니다!
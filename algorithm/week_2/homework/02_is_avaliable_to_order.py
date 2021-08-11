# 배달 가능 여부 반환
shop_menus = ["만두", "떡볶이", "오뎅", "사이다", "콜라"]
shop_orders = ["오뎅", "콜라", "만두"]


def is_available_to_order(menus, orders):
    menu_set = set(menus)
    for order in orders:
        if order not in menu_set:
            return False
    return True


result = is_available_to_order(shop_menus, shop_orders)
print(result)

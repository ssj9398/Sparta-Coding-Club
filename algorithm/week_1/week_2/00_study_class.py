class Person:
    def __init__(self, param_name):
        print("create",self);
        self.name = param_name

    def talk(self):
        print("나의 이름은",self.name)

person_1 = Person("a")
print(person_1.name)
print(person_1)
person_1.talk()
person_2 = Person("b")
print(person_2.name)
print(person_2)
person_2.talk()
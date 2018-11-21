from random import randint


MIN_STR_LEN = 1
MAX_STR_LEN = 100
REPEAT_TESTS_COUNT = 1000
RANDOM_TESTS_COUNT = 1000


def gen_testfile():
    """ Кидать в корень проекта """
    with open("cmake-build-debug/test.txt", "w") as file:
        gen_repeated_tests(file, REPEAT_TESTS_COUNT)
        gen_random_tests(file, RANDOM_TESTS_COUNT)


def gen_repeated_tests(file, count: int):
    """
    {количество тестов}
    {длина строки} {повторяющийся символ} {строка}
    ...
    """
    file.write(str(count) + "\n")
    for i in range(count):
        length = randint(MIN_STR_LEN, MAX_STR_LEN)
        s = _get_repeated_string(length)
        file.write("{} {} {}\n".format(length, s[0], s))


def gen_random_tests(file, count: int):
    """
    {количество тестов}
    {длина первой строки} {первая строка} {длина второй строки} {вторая строка} {первая строка + вторая строка}
    ...
    """
    file.write(str(count) + "\n")
    for i in range(count):
        length_first = randint(MIN_STR_LEN, MAX_STR_LEN)
        length_second = randint(MIN_STR_LEN, MAX_STR_LEN)
        s1 = _get_random_string(length_first)
        s2 = _get_random_string(length_second)
        append = s1 + s2
        file.write("{} {} {} {} {}\n".format(length_first, s1, length_second, s2, append))


def _get_repeated_string(length):
    return chr(randint(ord('A'), ord('z'))) * length


def _get_random_string(length):
    return "".join(chr(randint(ord('A'), ord('z'))) for _ in range(length))


if __name__ == "__main__":
    gen_testfile()

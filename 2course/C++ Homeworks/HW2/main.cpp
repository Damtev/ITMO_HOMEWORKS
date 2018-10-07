#include <iostream>
#include "Book.h"

int main() {
    auto *book = new Book();
    book->input();
    book->purchase();
}
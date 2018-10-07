//
// Created by damtev on 26.09.18.
//

#include <iostream>
#include <cstring>
#include "Book.h"

using namespace std;

void Book::input() {
    cout << "Enter book number: ";
    cin >> _bookNumber;
    string s;
    do {
        cout << "Enter book title < 21 symbols: ";
        cin >> s;
    } while (s.length() > 20);
    strcpy(_bookTitle, s.c_str());
    cout << "Enter book price: ";
    cin >> _price;
}

float Book::_totalCost(int n) {
    return _price * n;
}

void Book::purchase() {
    if (_price > 0) {
        cout << "Enter number of copies: ";
        int n;
        cin >> n;
        printf("Total cost of %d copies is %f", n, _totalCost(n));
    } else {
        cout << "Init a book before purchasing" << endl;
        input();
    }
}

//
// Created by damtev on 23.09.18.
//

#include <iostream>
#include <cstring>
#include "Student.h"

using namespace std;

template <typename T>
void input(const std::string &outMessage, T &in) {
    cout << "Enter " << outMessage << ": ";
    cin >> in;
}

float Student::_calculateTotal() {
    return _eng + _math + _science;
}

void Student::takeData() {
    input("administrative number", _administrativeNumber);
    input("name", _name);
    input("English mark", _eng);
    input("Math mark", _math);
    input("Science mark", _science);
    _total = _calculateTotal();
}

void Student::showData() {
    std::cout << "Number: " << _administrativeNumber << std::endl;
    std::cout << "Name: " << _name << std::endl;
    std::cout << "English: " << _eng << std::endl;
    std::cout << "Mathematics: " << _math << std::endl;
    std::cout << "Science: " << _science << std::endl;
    std::cout << "Total: " << _total << std::endl;
}
//
// Created by damtev on 28.11.18.
//

#include <iostream>
#include <typeinfo>

template <typename V>
void printValues(const V& value) {
	std::cout << typeid(value).name() << ": " << value << std::endl;
}

template <typename V, typename... T>
void printValues(const V& first, T... args) {
	printValues(first);
	printValues(args...);
}
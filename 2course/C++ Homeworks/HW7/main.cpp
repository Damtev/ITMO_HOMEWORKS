#include <iostream>
#include <unistd.h>
#include <cstring>
#include <vector>
#include "Array.hpp"

using std::cout;
using std::endl;

class Test {
public:
	Test() {
		std::cout << "Test::Test()" << std::endl;
	}

	void* operator new (std::size_t size) throw (std::bad_alloc) {
		std::cout << "Test::operator new(" << size << ")" << std::endl;
		return ::operator new(size);
	}
};

template <typename T>
bool less(T& a, T& b) {
	return a < b;
}

template <typename T>
struct Greater {
	bool operator()(T& a, T& b) {
		return b < a;
	}
};

void testMinimum() {
	cout << "Testing minimum: " << endl;

	cout << "	int: " << endl;
	Array<int> ints(3);
	ints[0] = 10;
	ints[1] = 2;
	ints[2] = 15;
	cout << minimum(ints, less<int>) << endl;
	cout << minimum(ints, Greater<int>()) << endl;

	cout << "	double: " << endl;
	Array<double> doubles(3);
	doubles[0] = 10.1;
	doubles[1] = 2.2;
	doubles[2] = 15.3;
	cout << minimum(doubles, less<double>) << endl;
	cout << minimum(doubles, Greater<double>()) << endl;

	cout << "======================================" << endl;
}

void testFlatten() {
	cout << "Testing flatten: " << endl;

	cout << "	Array of int: " << endl;
	Array<int> ints(2, 0);
	ints[0] = 10;
	ints[1] = 20;
	flatten(ints, std::cout);
	cout << endl;

	cout << "	Array of array of int: " << endl;
	Array< Array<int> > array_of_ints(2, ints);
	flatten(array_of_ints, cout);
	cout << endl;


	cout << "	Array of double: " << endl;
	Array<double> doubles(5, 1.65);
	flatten(doubles, cout);
	cout << endl;

	cout << "	Array of array of double: " << endl;
	Array< Array<double> > array_of_double(3, doubles);
	flatten(array_of_double, cout);
	cout << endl;

	cout << "======================================" << endl;
}

int main() {
	testMinimum();
	testFlatten();

	cout << "Demonstrating the difference between new and operator new(): " << endl;
	Test *t = new Test();
	void *p = Test::operator new(100); // 100 для различия в выводе
	delete t;
	operator delete(p);
	return 0;
}
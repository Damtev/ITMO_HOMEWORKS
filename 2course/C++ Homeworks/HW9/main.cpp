#include <iostream>
#include <tuple>
#include "Print_values.cpp"
#include "To_pair.cpp"
#include "Array.hpp"
#include "Functions.cpp"

using namespace std;

int main() {
	printValues(0, 3.5, "Hello");
	std::cout << "===============================" << std::endl;

	auto t = std::make_tuple(0, 7.62, "Bye");
	std::pair<double, char const *> p = toPair<1, 2>(t);
	printValues(p.first, p.second);
	std::cout << "===============================" << std::endl;

	Array<int> ints(3);
	ints[0] = 10;
	ints[1] = 2;
	ints[2] = 15;

	Array<Array<int>> arrayOfArray(4, ints);
	Array<Array<int>> moveCopy;
	moveCopy = std::move(arrayOfArray);
	std::cout << arrayOfArray.size() << " " << moveCopy.size() << std::endl;
	flatten(moveCopy, std::cout);

	cout << "========== TEST MOVE ==========\n";
	Array<int> first(5, 8);
	Array<int> second(std::move(first));
	cout << "First -> second:\n  First: ";
	flatten(first, std::cout);
	cout << "\n";
	cout << "  Second: ";
	flatten(second, std::cout);
	cout << "\n";

	first = std::move(second);
	cout << "Second -> first:\n  First: ";
	flatten(first, std::cout);
	cout << "\n";
	cout << "  Second: ";
	flatten(second, std::cout);
	cout << "\n";
	return 0;
}
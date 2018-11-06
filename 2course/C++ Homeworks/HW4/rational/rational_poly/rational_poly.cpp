//
// Created by damtev on 17.10.18.
//

#include <iostream>
#include "../lib_rational/RationalPolynom.h"

int main() {
	std::cout << "Enter number of coefficients:";
	int n;
	std::cin >> n;
	std::cout << "Enter polynom coefficients:";
	std::vector<RationalNumber> coefficients;
	int64_t numerator;
	int64_t denominator;
	for (int i = 0; i < n; ++i) {
		std::cin >> numerator;
		std::cin >> denominator;
		coefficients.emplace_back(numerator, denominator);
	}
	RationalPolynom rationalPolynom(coefficients);
	std::cout << "Enter first value coefficients:";
	std::cin >> numerator;
	std::cin >> denominator;
	RationalNumber value1(numerator, denominator);
	std::cout << "Enter second value coefficients:";
	std::cin >> numerator;
	std::cin >> denominator;
	RationalNumber value2(numerator, denominator);
	RationalNumber result1(rationalPolynom.calculate(value1).reduce());
	RationalNumber result2(rationalPolynom.calculate(value2).reduce());
	std::cout << result1 << " ";
	if (result1 < result2) {
		std::cout << "< ";
	} else if (result1 > result2) {
		std::cout << "> ";
	} else {
		std::cout << "= ";
	}
	std::cout << result2 << std::endl;
	return 0;
}
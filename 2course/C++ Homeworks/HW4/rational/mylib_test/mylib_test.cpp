//
// Created by damtev on 19.12.18.
//

#include <iostream>
#include <vector>
#include <string>
#include <fstream>

#include "../lib_rational/RationalPolynom.h"

int main (int argc, char* argv[]) {
	if (argc <= 1) return 0;
	std::ifstream fin(argv[1]);
	long chr;
	fin >> chr;
	for (size_t z = 0; z < chr; ++z) {
		long n, numeratorT, denominatorT, ans;
		std::vector<RationalNumber> coefficients;
		fin >> numeratorT >> denominatorT;
		RationalNumber q1(numeratorT, denominatorT);
		fin >> numeratorT >> denominatorT;
		RationalNumber q2(numeratorT, denominatorT);
		fin >> n;
		std::cout << "n " << n << std::endl;
		for (size_t i = 0; i < n; ++i) {
			fin >> numeratorT >> denominatorT;
			std::cout << "numerator an denominator " << numeratorT << " " << denominatorT << std::endl;
			RationalNumber tmp(numeratorT, denominatorT);
			tmp.reduce();
			coefficients.push_back(tmp);
		}
		RationalPolynom polynom(coefficients);
		fin >> ans;
		std::cout << "Real ans " << ans << std::endl;
		RationalNumber ans1 = polynom.calculate(q1);
		RationalNumber ans2 = polynom.calculate(q2);
		int calculated = ans2._compareTo(ans1);
		std::cout << "Count " << calculated << std::endl;
		if (calculated != ans) return 1;
	}
	return 0;
}
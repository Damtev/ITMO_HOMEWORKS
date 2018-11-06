//
// Created by damtev on 17.10.18.
//

#include "../lib_rational/RationalPolynom.h"

RationalPolynom::RationalPolynom(const std::vector<RationalNumber> &coefficients) {
	if (coefficients.empty()) {
		throw std::invalid_argument("Empty coefficients are invalid");
	}
	_coefficients = coefficients;
}

RationalPolynom::RationalPolynom(const RationalPolynom &other) {
	_coefficients = other._coefficients;
}

RationalPolynom::RationalPolynom(RationalPolynom &&other) noexcept{
	_coefficients = std::move(other._coefficients);
}

RationalNumber RationalPolynom::calculate(const RationalNumber &value) {
	RationalNumber gornerCoefficient(_coefficients[0]);
	for (int i = 1; i < _coefficients.size(); ++i) {
		gornerCoefficient = _coefficients[i] + gornerCoefficient * value;
	}
	return gornerCoefficient;
}


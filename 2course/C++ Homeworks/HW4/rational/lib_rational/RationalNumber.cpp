//
// Created by damtev on 17.10.18.
//

#include <algorithm>
#include <iostream>
#include "../lib_rational/RationalNumber.h"

RationalNumber::RationalNumber(int64_t numerator, int64_t denominator) {
	if (denominator == 0) {
		throw std::invalid_argument("Divizion by zero");
	}
	_numerator = numerator;
	_denominator = denominator;
}

RationalNumber::RationalNumber(const RationalNumber &other) {
	_numerator = other._numerator;
	_denominator = other._denominator;
}

int64_t _gcd(int64_t numerator, int64_t denominator) {
//	int64_t newNumerator = this->_numerator;
//	int64_t denominator = this->_denominator;
	while (numerator != 0 && denominator != 0) {
		numerator > denominator ? numerator %= denominator : denominator %= numerator;
	}
	return numerator + denominator;
}

RationalNumber& RationalNumber::reduce() {
	RationalNumber& rationalNumber(*this);
	int64_t gcd = _gcd(_numerator, _denominator);
	rationalNumber._numerator /= gcd;
	rationalNumber._denominator /= gcd;
	return rationalNumber;
}

RationalNumber RationalNumber::operator+(const RationalNumber &other) const {
	int64_t resultNumerator  = _numerator * other._denominator + other._numerator * _denominator;
	int64_t resultDenominator = _denominator * other._denominator;
	return {resultNumerator, resultDenominator};
}

RationalNumber RationalNumber::operator-(const RationalNumber &other) const {
	int64_t resultNumerator  = _numerator * other._denominator - other._numerator * _denominator;
	int64_t resultDenominator = _denominator * other._denominator;
	return {resultNumerator, resultDenominator};
}

RationalNumber RationalNumber::operator*(const RationalNumber &other) const {
	int64_t resultNumerator  = _numerator * other._numerator;
	int64_t resultDenominator = _denominator * other._denominator;
	return {resultNumerator, resultDenominator};
}

RationalNumber RationalNumber::operator/(const RationalNumber &other) const {
	int64_t resultNumerator  = _numerator * other._denominator;
	int64_t resultDenominator = _denominator * other._numerator;
	return {resultNumerator, resultDenominator};
}

bool RationalNumber::operator<(const RationalNumber &other) const {
	return _compareTo(other) < 0;
}

bool RationalNumber::operator>(const RationalNumber &other) const {
	return _compareTo(other) > 0;
}

bool RationalNumber::operator==(const RationalNumber &other) const {
	return _compareTo(other) == 0;
}

bool RationalNumber::operator!=(const RationalNumber &other) const {
	return _compareTo(other) != 0;
}

int8_t RationalNumber::_compareTo(const RationalNumber &other) const {
	int64_t subtractNumerator = RationalNumber(*this - other)._numerator;
	if (subtractNumerator > 0) {
		return 1;
	} else if (subtractNumerator < 0) {
		return -1;
	} else {
		return 0;
	}
}

std::ostream& operator<<(std::ostream &out, const RationalNumber &rationalNumber) {
	out << rationalNumber._numerator;
	if (rationalNumber._denominator != 1) {
		out << "/" << rationalNumber._denominator;
	}
	return out;
}
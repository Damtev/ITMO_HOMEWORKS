//
// Created by damtev on 17.10.18.
//

#include <cstdint>
#include <ostream>

#ifndef HW4_RATIONALNUMBER_H
#define HW4_RATIONALNUMBER_H

class RationalNumber {

public:

	RationalNumber(int64_t numerator, int64_t denominator);

	RationalNumber(const RationalNumber& other);

	bool operator<(const RationalNumber& other) const;

	bool operator>(const RationalNumber& other) const;

	bool operator==(const RationalNumber& other) const;

	bool operator!=(const RationalNumber& other) const;

	RationalNumber operator+(const RationalNumber& other) const;

	RationalNumber operator-(const RationalNumber& other) const;

	RationalNumber operator*(const RationalNumber& other) const;

	RationalNumber operator/(const RationalNumber& other) const;

	RationalNumber& reduce();

private:

	int64_t _numerator;
	int64_t _denominator;

	int8_t _compareTo(const RationalNumber &other) const;

	friend std::ostream &operator << (std::ostream &out, const RationalNumber& rationalNumber);
};

int64_t _gcd(int64_t numerator, int64_t denominator);

std::ostream& operator<<(std::ostream &out, const RationalNumber &rationalNumber);

#endif //HW4_RATIONALNUMBER_H

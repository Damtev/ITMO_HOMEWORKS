//
// Created by damtev on 17.10.18.
//

#ifndef HW4_RATIONALPOLYNOM_H
#define HW4_RATIONALPOLYNOM_H

#include <vector>
#include <cstdint>
#include "RationalNumber.h"

class RationalPolynom {

public:

	explicit RationalPolynom(const std::vector<RationalNumber>& coefficients);

	RationalPolynom(const RationalPolynom& other);

	RationalPolynom(RationalPolynom&& other) noexcept;

	RationalNumber calculate(const RationalNumber& value);

private:

	std::vector<RationalNumber> _coefficients;
};

#endif //HW4_RATIONALPOLYNOM_H

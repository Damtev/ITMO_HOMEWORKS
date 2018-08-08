#include <algorithm>
#include <iostream>
#include <sstream>
#include "BigInteger.h"

const int BASE_STANDART = 1000000000;
const int LENGTH_STANDART = 9;

template<typename T, size_t Base>
BigInteger<T, Base>::BigInteger(long long value) {
    if (value < 0) {
        sign = -1;
        value *= -1;
    } else {
        sign = 1;
    }
    data.clear();
    size = 0;
    while (value > 0) {
        T cur_digit = 0;
        std::vector<T> cur_blocks;
        for (size_t i = 0; i < BLOCKS_NUMBER && value > 0; ++i) {
            cur_blocks.push_back(value % Base);
            ++size;
            value /= Base;
        }
        std::reverse(cur_blocks.begin(), cur_blocks.end());
        for (size_t i = 0; i < cur_blocks.size(); ++i) {
            cur_digit = cur_digit << BITS_FOR_DIGIT_NUMBER;
            cur_digit += cur_blocks[i];
        }
        data.push_back(cur_digit);
    }
}

template<typename T, size_t Base>
BigInteger<T, Base>::BigInteger(const std::vector<T>& vector, const int sign) {
    data = vector;
    this->sign = sign;
    simplify();
}

template<typename T, size_t Base>
BigInteger<T, Base>::BigInteger(const std::string &string) {
    std::vector<int> digits;
    int pos = 0, sign = 1;
    if (string[0] == '-') {
        sign = -1;
        ++pos;
    }
    for (int block_index = (int) (string.size()) - 1; block_index >= pos; block_index -= LENGTH_STANDART) {
        int cur_block = 0;
        for (int in_block_index = std::max(pos, block_index - LENGTH_STANDART + 1);
             in_block_index <= block_index; in_block_index++) {
            cur_block = cur_block * 10 + (string[in_block_index] - '0');
        }
        digits.push_back(cur_block);
    }
    BigInteger<int, BASE_STANDART> bigint_in_standart_base(digits, sign);
    *this = static_cast<BigInteger<T, Base>>(bigint_in_standart_base);
}

template<typename T, size_t Base>
template<typename NewT, size_t NewBase>
BigInteger<T, Base>::operator BigInteger<NewT, NewBase>() {
    BigInteger<NewT, 10> power(1);
    BigInteger<NewT, 10> result(0);
    BigInteger<NewT, 10> big_in_base(Base);
    BigInteger<NewT, 10> big_in_NewBase(NewBase);
    for (size_t i = 0; i < Size(); ++i) {
        BigInteger<NewT, 10> digit((*this)[i]);
        BigInteger<NewT, 10> cur_result;
        multiply(power, digit, cur_result);
        result += cur_result;
        multiply(power, big_in_base, power);
    }
    NewT number_to_data = 0;
    std::vector<NewT> result_data;
    int index = 0;
    size_t result_bits_number = ceil(log2(NewBase));
    size_t result_blocks_number = sizeof(NewT) * 8 / result_bits_number;
    NewT remind = 0;
    while (result.Size() > 0) {
        remind = result % NewBase;
        result /= NewBase;
        number_to_data += (remind << (index % result_blocks_number)
                                     * result_bits_number);
        if ((index % result_blocks_number) == result_blocks_number - 1) {
            result_data.push_back(number_to_data);
            number_to_data = 0;
        }
        ++index;
    }
    if (number_to_data != 0) {
        result_data.push_back(number_to_data);
    }
    BigInteger<NewT, NewBase> ans(result_data, sign);
    return ans;
}

template<typename T, size_t Base>
void multiply(const BigInteger<T, Base> &first,
              const BigInteger<T, Base> &second, BigInteger<T, Base> &result) {
    std::vector<T> result_digits(first.Size() + second.Size(), 0);
    std::vector<T> result_data;
    for (size_t i = 0; i < first.Size(); ++i) {
        unsigned long long carry = 0;
        for (size_t j = 0; j < second.Size() || carry; ++j) {
            unsigned long long cur =
                    result_digits[i + j] + first[i] * (j < (int) (second.Size()) ? second[j] : 0) + carry;
            result_digits[i + j] = cur % Base;
            carry = cur / Base;
        }
    }
    T number_to_data = 0;
    for (size_t i = 0; i < result_digits.size(); ++i) {
        number_to_data += (result_digits[i] << ((i % first.BLOCKS_NUMBER)
                                                * first.BITS_FOR_DIGIT_NUMBER));
        if ((i % first.BLOCKS_NUMBER) == first.BLOCKS_NUMBER - 1) {
            result_data.push_back(number_to_data);
            number_to_data = 0;
        }
    }
    if (number_to_data != 0) {
        result_data.push_back(number_to_data);
    }
    result.data = result_data;
    result.sign = first.sign * second.sign;
    result.simplify();
}

template<typename T, size_t Base>
BigInteger<T, Base> &BigInteger<T, Base>::add(BigInteger &bigInteger) const {
    T carry = 0;
    for (size_t i = 0; i < std::max(bigInteger.data.size(),
                                    data.size()) || carry; ++i) {
        if (i == bigInteger.data.size()) {
            bigInteger.data.push_back(0);
            bigInteger.size += BLOCKS_NUMBER;
        }
        T number_to_data = 0;
        for (size_t shift_in_block = 0; shift_in_block < BLOCKS_NUMBER;
             ++shift_in_block) {
            T first = bigInteger[i * BLOCKS_NUMBER + shift_in_block];
            T second = (i < data.size()) ?
                       (*this)[i * BLOCKS_NUMBER + shift_in_block] : 0;
            number_to_data = number_to_data + (((first + second + carry) % T(Base))
                    << shift_in_block * BITS_FOR_DIGIT_NUMBER);
            carry = (first + second + carry >= T(Base)) ? 1 : 0;
        }
        bigInteger.data[i] = number_to_data;
    }
    bigInteger.simplify();
    return bigInteger;
}

//template<typename T, size_t Base>
//BigInteger<T, Base> &BigInteger<T, Base>::subtract(const BigInteger &bigInteger) {
//    T carry = 0;
//    for (size_t i = 0; i < bigInteger.data.size() || carry; ++i) {
//        T number_to_data = 0;
//        for (size_t shift_in_block = 0; shift_in_block < BLOCKS_NUMBER;
//             ++shift_in_block) {
//            T first = (*this)[i * BLOCKS_NUMBER + shift_in_block];
//            T second = (i < bigInteger.data.size()) ?
//                       bigInteger[i * BLOCKS_NUMBER + shift_in_block] : 0;
//            number_to_data = number_to_data + (((first + T(Base) - second - carry) % T(Base))
//                    << shift_in_block * BITS_FOR_DIGIT_NUMBER);
//            carry = (first < second + carry) ? 1 : 0;
//        }
//        data[i] = number_to_data;
//    }
//    simplify();
//    return *this;
//}

template<typename T, size_t Base>
BigInteger<T, Base> &BigInteger<T, Base>::subtract
        (const BigInteger &subtrahend, const BigInteger &minuend, BigInteger &result) {
    T carry = 0;
    for (size_t i = 0; i < minuend.data.size() || carry; ++i) {
        T number_to_data = 0;
        for (size_t shift_in_block = 0; shift_in_block < subtrahend.BLOCKS_NUMBER;
             ++shift_in_block) {
            T first = subtrahend[i * subtrahend.BLOCKS_NUMBER + shift_in_block];
            T second = (i < minuend.data.size()) ?
                       minuend[i * subtrahend.BLOCKS_NUMBER + shift_in_block] : 0;
            number_to_data = number_to_data + (((first + T(Base) - second - carry) % T(Base))
                    << shift_in_block * subtrahend.BITS_FOR_DIGIT_NUMBER);
            carry = (first < second + carry) ? 1 : 0;
        }
        result.data[i] = number_to_data;
    }
    result.simplify();
    return result;
}

template<typename T, size_t Base>
BigInteger<T, Base>::BigInteger(const BigInteger &bigInteger) {
    data = bigInteger.data;
    sign = bigInteger.sign;
    size = bigInteger.size;
}

template<typename T, size_t Base>
BigInteger<T, Base> &BigInteger<T, Base>::operator=(const BigInteger<T, Base> &bigInteger) {
    data = bigInteger.data;
    sign = bigInteger.sign;
    size = bigInteger.size;
    return *this;
}

template<typename T, size_t Base>
BigInteger<T, Base>::BigInteger(BigInteger &&bigInteger) {
    data = std::move(bigInteger.data);
    sign = bigInteger.sign;
    size = bigInteger.size;
}

template<typename T, size_t Base>
size_t BigInteger<T, Base>::Size() const {
    return size;
}

//битовая магия
template<typename T, size_t Base>
T BigInteger<T, Base>::operator[](size_t index) const {
    size_t pos_in_data = index / BLOCKS_NUMBER;
    size_t shift_in_block = index % BLOCKS_NUMBER;
    T shift_in_data = data[pos_in_data] >>
                                        (BITS_FOR_DIGIT_NUMBER * shift_in_block);
    T bit_mask = (T(1) <<  BITS_FOR_DIGIT_NUMBER) - 1;
    T result = shift_in_data & bit_mask;
    return result;
}

template<typename T, size_t Base>
BigInteger<T, Base> &BigInteger<T, Base>::operator+=(const BigInteger &bigInteger) {
    /*if (sign == bigInteger.sign) {
        return bigInteger.add(*this);
    } else {
        return *this -= (-bigInteger);
    }*/
    if (sign == bigInteger.sign) {
        return bigInteger.add(*this);
    } else {
        if (sign < 0) {
            sign = 1;
            if (*this < bigInteger) {
                return subtract(bigInteger, *this, *this);
            } else {
                sign = -1;
                subtract(*this, bigInteger, *this);
                return *this;
            }
        } else {
            sign = -1;
            if (*this < bigInteger) { //наше по модулю больше
                sign = 1;
                return subtract(*this, bigInteger, *this);
            } else {
                return subtract(bigInteger, *this, *this);
            }
        }
    }
}

template<typename T, size_t Base>
BigInteger<T, Base> &BigInteger<T, Base>::operator-=(const BigInteger &bigInteger) {
    /*if (sign == bigInteger.sign) {
        if (abs() >= bigInteger.abs()) {
//            return subtract(bigInteger);
            return subtract(*this, bigInteger, *this);
        }
        return *this = -(bigInteger - *this);
    }
    return *this += (-bigInteger);*/
    sign = -sign;
    *this += bigInteger;
    if (!data.empty()) {
        sign = -sign;
    }
//    simplify();
    return *this;
}

template<typename T, size_t Base>
BigInteger<T, Base>  BigInteger<T, Base>::operator+(const BigInteger &bigInteger) const {
//    BigInteger result = *this;
//    return result += bigInteger;
    BigInteger<T, Base> result = *this;
    result += bigInteger;
    return result;
}

template<typename T, size_t Base>
BigInteger<T, Base>  BigInteger<T, Base>::operator-(const BigInteger &bigInteger) const {
//    BigInteger result = *this;
//    return result -= bigInteger;
    BigInteger<T, Base> result = *this;
    result -= bigInteger;
    return result;
}

template<typename T, size_t Base>
BigInteger<T, Base> BigInteger<T, Base>::operator*
        (const BigInteger &bigInteger) const {
    BigInteger<T, Base> ans(0);
    multiply(*this, bigInteger, ans);
    return ans;
}

template<typename T, size_t Base>
BigInteger<T, Base> BigInteger<T, Base>::operator*(long long value) const {
    BigInteger<T, Base> bigInteger(value);
    *this *= bigInteger;
    return *this;
}

template<typename T, size_t Base>
long long BigInteger<T, Base>::operator%(long long value) const{
    if (Size() == 0) {
        return 0;
    }
    unsigned long long remind = 0;
    for (int i = (int) (size) - 1; i >= 0; i--) {
        remind = (remind * Base + (*this)[i]) % value;
    }
    return remind;
}

template<typename T, size_t Base>
long long BigInteger<T, Base>::operator%=(long long value) const {
    long long res = (*this) % value;
    return res;
}


template<typename T, size_t Base>
BigInteger<T, Base> & BigInteger<T, Base>::operator*=
        (const BigInteger &bigInteger) {
    if (Size() == 0) {
        return *this;
    }
    BigInteger<T, Base> answer(0);
    multiply(*this, bigInteger, answer);
    return *this;
}

template<typename T, size_t Base>
BigInteger<T, Base> & BigInteger<T, Base>::operator/=(long long value) {
    if (Size() == 0) {
        return *this;
    }
    if (value < 0) {
        sign *= -1;
        value *= -1;
    }
    unsigned long long remind = 0;
    T number_to_data = 0;
    int index = data.size() - 1;
    for (int i = int(Size()) - 1; i >= 0; --i) {
        unsigned long long cur_digit = remind * Base + (*this)[i];
        remind = cur_digit % value;
        number_to_data += (T(cur_digit / value)
                << ((i % BLOCKS_NUMBER) * BITS_FOR_DIGIT_NUMBER));
        if (i % BLOCKS_NUMBER == 0) {
            data[index] = number_to_data;
            number_to_data = 0;
            --index;
        }
    }
    if (number_to_data != 0 || remind) {
        simplify();
    }
    return *this;
}

template<typename T, size_t Base>
BigInteger<T, Base> BigInteger<T, Base>::operator/(long long value) const {
    BigInteger result = *this;
    result /= value;
    return result;
}

template<typename T, size_t Base>
BigInteger<T, Base> BigInteger<T, Base>::operator-() const {
    BigInteger<T, Base> res = *this;
    res.sign = -sign;
    return res;
}

template<typename T, size_t Base>
bool BigInteger<T, Base>::operator<(const BigInteger &bigInteger) const {
    if (sign != bigInteger.sign)
        return sign < bigInteger.sign;
    if (size != bigInteger.size) {
        bool less = size < bigInteger.size;
        if (sign < 0) {
            less = !less;
        }
        return less;
    }
    for (int i = (int) (bigInteger.size) - 1; i >= 0; --i)
        if ((*this)[i] != bigInteger[i])
            return (*this)[i] * sign < bigInteger[i] * bigInteger.sign;
    return false;
}

template<typename T, size_t Base>
bool BigInteger<T, Base>::operator>(const BigInteger &bigInteger) const {
    return bigInteger < *this;
}

template<typename T, size_t Base>
bool BigInteger<T, Base>::operator<=(const BigInteger &bigInteger) const {
    return !(bigInteger < *this);
}

template<typename T, size_t Base>
bool BigInteger<T, Base>::operator>=(const BigInteger &bigInteger) const {
    return !(*this < bigInteger);
}

template<typename T, size_t Base>
bool BigInteger<T, Base>::operator==(const BigInteger &bigInteger) const {
    return !(*this < bigInteger) && !(bigInteger < *this);
}

template<typename T, size_t Base>
bool BigInteger<T, Base>::operator!=(const BigInteger &bigInteger) const {
    return !(*this == bigInteger);
//    return (*this < bigInteger) || (bigInteger < *this);
}

template<typename T, size_t Base>
BigInteger<T, Base> BigInteger<T, Base>::abs() const {
    BigInteger<T, Base> res = *this;
    res.sign *= 1;
    return res;
}

template<typename T, size_t Base>
void BigInteger<T, Base>::simplify() {
    while (!data.empty() && data.back() == 0) {
        data.pop_back();
    }
    size = BLOCKS_NUMBER * data.size();
    while (!data.empty() && (*this)[size - 1] == 0) {
        --size;
    }
    if (data.empty()) {
        sign = 1;
    }
}

template<typename T, size_t Base>
std::string BigInteger<T, Base>::toString(BigInteger<T, Base> &bigInteger) {
    auto tmp = static_cast<BigInteger<int, 10>>(bigInteger);
//    std::sstream stream;
    std::ostringstream stream;
    if (tmp.sign == -1) {
        stream << '-';
    }
    if (tmp.Size() == 0) {
        stream << 0;
    } else {
        for (int i = int(tmp.Size()) - 1; i >= 0; i--) {
            stream << tmp[i];
        }
    }
    std::string result;
//    stream >> result;
//    return result;
    return stream.str();
}

template<typename T, size_t Base>
std::ostream & operator<<(std::ostream &out, BigInteger<T, Base> &bigInteger) {
    auto bigInteger_in_10 = static_cast<BigInteger<int, 10>>(bigInteger);
    if (bigInteger_in_10.sign == -1) {
        out << '-';
    }
    if (bigInteger_in_10.Size() == 0) {
        out << 0;
    } else {
        for (int i = (int) (bigInteger_in_10.Size()) - 1; i >= 0; --i) {
            out << bigInteger_in_10[i];
        }
    }
    return out;
}

template<typename T, size_t Base>
std::istream & operator>>(std::istream &in, BigInteger<T, Base> &bigInteger) {
    std::string s;
    in >> s;
    bigInteger = BigInteger<T, Base>(s);
    return in;
}

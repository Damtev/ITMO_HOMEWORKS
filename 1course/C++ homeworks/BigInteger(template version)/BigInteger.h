#pragma once

#include <iostream>
#include <vector>
#include <cmath>

template<typename T, size_t Base = std::numeric_limits<T>::max()>
class BigInteger;

template<typename T, size_t Base>
class BigInteger {
public:
    explicit BigInteger(long long value = 0);
    explicit BigInteger(const std::string &string);
    explicit BigInteger(const std::vector<T> &vector, const int sign);
    template<typename NewT, size_t NewBase>
    explicit operator BigInteger<NewT, NewBase>();

    BigInteger(const BigInteger &bigInteger);
    BigInteger(BigInteger &&bigInteger);

    T operator[](size_t index) const;

    BigInteger &operator=(const BigInteger &bigInteger);
    bool operator<(const BigInteger &bigInteger) const;
    bool operator>(const BigInteger &bigInteger) const;
    bool operator<=(const BigInteger &bigInteger) const;
    bool operator>=(const BigInteger &bigInteger) const;
    bool operator==(const BigInteger &bigInteger) const;
    bool operator!=(const BigInteger &bigInteger) const;

    BigInteger  &operator +=(const BigInteger &bigInteger);
    BigInteger operator +(const BigInteger &bigInteger) const;
    BigInteger &operator -=(const BigInteger &bigInteger);
    BigInteger operator -(const BigInteger &bigInteger) const;
    BigInteger &operator *=(const BigInteger &bigInteger);
    BigInteger operator *(const BigInteger &bigInteger) const;

    BigInteger operator *(long long val) const;
    BigInteger operator /(long long value) const;
    BigInteger &operator /=(long long value);
    long long operator %=(long long value) const;
    long long operator %(long long value) const;
    BigInteger operator-() const;

    size_t Size() const;
    BigInteger abs() const;
    std::string toString(BigInteger &bigInteger);
    BigInteger &add(BigInteger &bigInteger) const;
    BigInteger &subtract(const BigInteger &subtrahend,
                         const BigInteger &minuend, BigInteger &result);
    int sign;

private:
    void simplify();
    template<typename U, size_t base>
    friend std::ostream & operator << (std::ostream &out, BigInteger<U, base> &bigInteger);
    template<typename U, size_t base>
    friend std::istream & operator >> (std::istream &in, BigInteger<U, base> &bigInteger);
    template<typename U, size_t base>
    friend void multiply(const BigInteger<U, base> &first, const BigInteger<U, base> &second, BigInteger<U, base> &result);



    std::vector<T> data;
    size_t size; //число разрядов в данной системе счисления
    const size_t BITS_FOR_DIGIT_NUMBER = ceil(log2(Base)); //количество битов для одной цифры
    const size_t BLOCKS_NUMBER = sizeof(T) * 8 / BITS_FOR_DIGIT_NUMBER; //количество блоков по BITS_FOR_DIGIT_NUMBER
};
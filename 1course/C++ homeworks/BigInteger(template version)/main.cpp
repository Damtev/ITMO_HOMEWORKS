#include <iostream>
#include <cassert>
#include <fstream>
#include "BigInteger.cpp"

using namespace std;

void test1() {
    ifstream in("test.txt");
    int failed_count = 0;
    int passed_count = 0;
    for (int i = 0; i < 1000; ++i) {
        BigInteger<int64_t, BASE_STANDART> a;
        BigInteger<int64_t, BASE_STANDART> b;
        in >> a;
        in >> b;
        BigInteger<int64_t, BASE_STANDART> a_sum_b;
        BigInteger<int64_t, BASE_STANDART> b_sum_a;
        BigInteger<int64_t, BASE_STANDART> a_sub_b;
        BigInteger<int64_t, BASE_STANDART> b_sub_a;
        BigInteger<int64_t, BASE_STANDART> a_mult_b;
        BigInteger<int64_t, BASE_STANDART> b_mult_a;

        in >> a_sum_b;
        BigInteger<int64_t, BASE_STANDART> result = a + b;
        if (a_sum_b == result) {
            ++passed_count;
        } else {
            cout << "a:\t" << a << endl;
            cout << "b:\t" << b << endl;
            cout << "Expected a + b:\t" << a_sum_b << endl;
            cout << "In result a + b:\t" << result << endl;
            ++failed_count;
        }

        in >> b_sum_a;
        result = b + a;
        if (b_sum_a == result) {
            ++passed_count;
        } else {
            cout << "a:\t" << a << endl;
            cout << "b:\t" << b << endl;
            cout << "Expected b + a:\t" << b_sum_a << endl;
            cout << "In result b + a:\t" << result << endl;
            ++failed_count;
        }

        in >> a_sub_b;
        result = a - b;
        if (a_sub_b == result) {
            ++passed_count;
        } else {
            cout << "a:\t" << a << endl;
            cout << "b:\t" << b << endl;
            cout << "Expected a - b:\t" << a_sub_b << endl;
            cout << "In result a - b:\t" << result << endl;
            ++failed_count;
        }

        in >> b_sub_a;
        result = b - a;
        if (b_sub_a == result) {
            ++passed_count;
        } else {
            cout << "a:\t" << a << endl;
            cout << "b:\t" << b << endl;
            cout << "Expected b - a:\t" << b_sub_a << endl;
            cout << "In result b - a:\t" << result << endl;
            ++failed_count;
        }

        in >> a_mult_b;
        result = a * b;
        if (a_mult_b == result) {
            ++passed_count;
        } else {
            cout << "a:\t" << a << endl;
            cout << "b:\t" << b << endl;
            cout << "Expected a * b:\t" << a_mult_b << endl;
            cout << "In result a * b:\t" << result << endl;
            ++failed_count;
        }

        in >> b_mult_a;
        result = b * a;
        if (b_mult_a == result) {
            ++passed_count;
        } else {
            cout << "a:\t" << a << endl;
            cout << "b:\t" << b << endl;
            cout << "Expected b * a:\t" << b_mult_a << endl;
            cout << "In result b * a:\t" << result << endl;
            ++failed_count;
        }
    }
    cout << endl;
    cout << "Tests failed:  " << failed_count << endl;
    cout << "Tests passed:  " << passed_count << endl;
    cout << "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" << endl;
}

void test2() {
    ifstream in("test1.txt");
    BigInteger<int64_t, 10> a;
    BigInteger<int64_t, 10> b;
    in >> a;
    in >> b;
    cout << "a: " << a << endl;
    cout << "b: " << b << endl;
    BigInteger<int64_t, 10> add = a + b;
    BigInteger<int64_t, 10> sub = a - b;
    BigInteger<int64_t, 10> mul = a * b;
    cout << "Answer a + b: " << add << "       Expected: 123456780000000000000000000000000" << endl;
    add = b + a;
    cout << "Answer b + a: " << add << "       Expected: 123456780000000000000000000000000" << endl;
    cout << "Answer a - b: " << sub << "       Expected: 123456799753086424691357975308642" << endl;
    sub = b - a;
    cout << "Answer b - a: " << sub << "       Expected: -123456799753086424691357975308642" << endl;
    cout << "Answer a * b: " << mul << "       Expected: -1219326320073159600060966085962505712543820210333789971041" << endl;
    mul = b * a;
    cout << "Answer b * a: " << mul << "       Expected: -1219326320073159600060966085962505712543820210333789971041" << endl;
    cout << "_________________________" << endl << endl;

    BigInteger<int64_t, 10> a1;
    BigInteger<int64_t, 10> b1;
    in >> a1;
    in >> b1;
    BigInteger<int32_t, 57> c = static_cast<BigInteger<int32_t, 57>>(a1);
    BigInteger<int32_t, 57> d = static_cast<BigInteger<int32_t, 57>>(b1);
    cout << "c: " << c << endl;
    cout << "d: " << d << endl;
    BigInteger<int32_t, 57> add1 = c + d;
    BigInteger<int32_t, 57> sub1 = c - d;
    BigInteger<int32_t, 57> mul1 = c * d;
    cout << "Answer c + d: " << add1 << "       Expected: -6522152067452" << endl;
    add1 = d + c;
    cout << "Answer d + c: " << add1 << "       Expected: -6522152067452" << endl;
    cout << "Answer c - d: " << sub1 << "       Expected: 6444037294522" << endl;
    sub1 = d - c;
    cout << "Answer d - c: " << sub1 << "       Expected: -6444037294522" << endl;
    cout << "Answer c * d: " << mul1 << "       Expected: 253212734444495146640955" << endl;
    mul1 = d * c;
    cout << "Answer d * c: " << mul1 << "       Expected: 253212734444495146640955" << endl;
    cout << "_________________________" << endl << endl;
}

int main() {
    test1();
    test2();
//    BigInteger<int64_t, BASE_STANDART> a(46);
//    BigInteger<int64_t, BASE_STANDART> b(-35);
//    BigInteger<int64_t, BASE_STANDART> sum = a + b;
//    cout << sum;
//    a.subtract(b, a, a);
//    b.subtract(b, a, b);
//    cout << a << endl;
//    cout << b;
    return 0;
}
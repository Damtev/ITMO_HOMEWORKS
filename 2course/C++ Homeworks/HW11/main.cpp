#include <iostream>
#include <sstream>
#include "StringParseException.h"

using namespace std;

template<typename T>
T fromString(const string &s) {
    istringstream in(s);
    noskipws(in);
    T result;
    if (!(in >> result) || (in.get() && !in.eof())) {
        throw StringParseException("Can't cast string '" + s + "' to " + typeid(T).name());
    }
    return result;
}

template <>
string fromString(const string &s) {
    return s;
}

template<typename T>
void printResult(const T &value) {
    cout << value << endl;
}

void testString(const string& s) {
    try {
        printResult(fromString<int>(s));
    } catch (StringParseException &error) {
        cout << error.what() << endl;
    }
    try {
        printResult(fromString<double>(s));
    } catch (StringParseException &error) {
        cout << error.what() << endl;
    }
    try {
        printResult(fromString<string>(s));
    } catch (StringParseException &error) {
        cout << error.what() << endl;
    }
}

void test() {
        string s1("1a23");
        testString(s1);
        cout << endl;

        string s2("12.3");
        testString(s2);
        cout << endl;

        string s3("abc");
        testString(s3);
        cout << endl;

        string s4;
        testString(s4);
        cout << endl;

        string s5(" ");
        testString(s5);
}

int main() {
    test();
    return 0;
}
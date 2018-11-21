#include <iostream>
#include <cassert>
#include "String.h"

using namespace std;

int main() {
	String s1("Hello,");
	String s2(" world!");
	s1.append(s2);
	cout << s1 << endl;

	String s("Hello");
	s.append(s);
	cout << s << endl;

	auto s3 = new String(s1);
	cout << *s3 << endl;
	delete s3;

	freopen("test.txt", "r", stdin);

	int n;
	cin >> n;

	size_t length;
	char ch;
	string expected;
	int count = 0;
	for (int i = 1; i <= n; ++i) {
		cin >> length;
		cin >> ch;
		cin >> expected;

		String myString(length, ch);
		String copy(myString);
		if (copy == expected) {
			cout << "Test " << i << " passed" << endl;
			++count;
		} else {
			cout << "Test " << i << " failed" << endl;
			cout << "expected: " << expected << " " << "actual: " << copy << endl;
		}
	}
	cout << "================================================================" << endl;
	cout << "Tests passed: " << count << endl;

	return 0;
}
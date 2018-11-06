#include <iostream>
#include "String.h"

int main() {
	String s1("Hello,");
	String s2(" world!");
	s1.append(s2);
	std::cout << s1 << std::endl;

	String s("Hello");
	s.append(s);
	std::cout << s << std::endl;

	s2 = s;
	std::cout << s2 << std::endl;

	auto* s3 = new String(s1);
	std::cout << *s3 << std::endl;

	return 0;
}
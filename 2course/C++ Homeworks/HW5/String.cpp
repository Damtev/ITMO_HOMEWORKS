//
// Created by damtev on 23.10.18.
//

#include <cstring>
#include "String.h"
#include <iostream>

String::String(const char* str) {
	/*_size = strlen(str); //возвращает длину строки без завершающего нулевого байта
	_str = new char[_size + 1];
	strcpy(_str, str);*/
	*this = str;
}

String::String(const String& other) {
	/*_size = other._size;
	_str = new char[_size + 1];
	strcpy(_str, other._str);*/
	*this = other;
}

String::String(size_t size, char symbol) {
	_size = size;
	_str = new char[size + 1];
	for (size_t i = 0; i < size; ++i) {
		_str[i] = symbol;
	}
	_str[size] = '\0';
}

String::~String() {
	_size = 0;
	delete[] _str;
}

String& String::operator=(const char* str) {
	_size = strlen(str); //возвращает длину строки без завершающего нулевого байта
	_str = new char[_size + 1];
	strcpy(_str, str);
	return *this;
}

String& String::operator=(const String& other) {
	_size = other._size;
	_str = new char[_size + 1];
	strcpy(_str, other._str);
	return *this;
}

void String::append(const String& other) {
	size_t resultSize = _size + other._size;
	char* resultStr = new char[resultSize + 1];
	strcpy(resultStr, _str);
	strcat(resultStr, other._str); //присоединяет str к resultStr, завершающий нулевой байт строки resultStr стирается
	*this = resultStr;
}

std::ostream& operator<<(std::ostream &out, const String &string) {
	out << string._str;
	return out;
}

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
	_size = _size + other._size;
	char* resultStr = new char[_size + 1];
	strcpy(resultStr, _str);
	strcat(resultStr, other._str); // Нулевой символ конца строки destptr заменяется первым символом строки srcptr,
	delete[] _str; // и новый нуль-символ добавляется в конец уже новой строки
	_str = resultStr;
}

std::ostream& operator<<(std::ostream &out, const String &string) {
	out << string._str;
	return out;
}

bool String::operator==(const std::string& string) {
	return (_str == string);
}

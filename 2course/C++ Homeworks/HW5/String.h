//
// Created by damtev on 23.10.18.
//

#ifndef HW5_STRING_H
#define HW5_STRING_H

#include <string>

class String {
public:
	String(const char* str);
	String(const String& other);
	String(size_t size, char symbol);

	~String();

	String& operator=(const char* str);
	String& operator=(const String& other);

	bool operator==(const std::string& string);

	void append(const String& other);

private:
	size_t _size;
	char* _str;
//	char _str[];

	friend std::ostream &operator << (std::ostream &out, const String& string);
};

std::ostream& operator<<(std::ostream &out, const String& string);

#endif //HW5_STRING_H
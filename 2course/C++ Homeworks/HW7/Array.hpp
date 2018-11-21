//
// Created by damtev on 14.11.18.
//

#ifndef HW7_ARRAY_HPP
#define HW7_ARRAY_HPP

#include <cstddef>
#include <iostream>
#include <iosfwd>

template <typename T>
class Array {
public:

	explicit Array(size_t size = 0, const T& value = T());

	Array(const Array&);

	~Array();

	Array& operator=(const Array& array);

	size_t size() const;

	T& operator[](size_t i);

	const T& operator[](size_t i) const;
private:

	size_t mSize;
	T* mData;
};

#include "Array.cpp"

template <typename T, typename Comparator>
T& minimum(Array<T> array, Comparator comparator) {
	T& min = array[0];
	for (int i = 1; i < array.size(); ++i) {
		if (comparator(array[i], min)) {
			min = array[i];
		}
	}
	return min;
}

template <typename T>
void flatten(const Array<T>& array, std::ostream& out) {
	for (int i = 0; i < array.size(); ++i) {
		out << array[i] << " ";
	}
}

template <typename T>
void flatten(const Array<Array<T>>& array, std::ostream& out) {
	for (int i = 0; i < array.size(); ++i) {
		flatten(array[i], out);
	}
}

#endif //HW7_ARRAY_HPP

//
// Created by damtev on 14.11.18.
//

#pragma once

#include <cstddef>
#include <iostream>
#include <iosfwd>

template <typename T>
class Array {
public:

	explicit Array(size_t size = 0, const T& value = T());

	Array(const Array& array);

	Array(Array&& array) noexcept;

	~Array();

	Array& operator=(const Array& array);

	Array& operator=(Array&& array) noexcept;

	size_t size() const;

	T& operator[](size_t i);

	const T& operator[](size_t i) const;
private:

	size_t mSize;
	T* mData;

	void del(T* data);
};

#include "Array.cpp"
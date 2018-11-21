//
// Created by damtev on 14.11.18.
//

#pragma once

#include <new>
#include "Array.hpp"

template<typename T>
Array<T>::Array(size_t size, const T& value) {
	mSize = size;
	mData = static_cast<T*>(operator new[](mSize * sizeof(T)));
	for (int i = 0; i < mSize; ++i) {
		new (mData + i) T(value);
	}
}

template<typename T>
Array<T>::Array(const Array& array) {
	mSize = array.size();
	mData = static_cast<T*>(operator new[](mSize * sizeof(T)));
	for (int i = 0; i < mSize; ++i) {
		new (mData + i) T(array[i]);
	}
}

template<typename T>
Array<T>::~Array() {
	for (int i = 0; i < mSize; ++i) {
		mData[i].~T();
	}
	operator delete[](mData);
	mSize = 0;
}

template<typename T>
Array<T>& Array<T>::operator=(const Array& array) {
	delete[] mData;
	mSize = array.size();
	mData = static_cast<T*>(operator new[](mSize * sizeof(T)));
	for (int i = 0; i < mSize; ++i) {
		new (mData + i) T(array[i]);
	}
	return *this;
}

template<typename T>
size_t Array<T>::size() const {
	return mSize;
}

template<typename T>
T& Array<T>::operator[](size_t i) {
	return mData[i];
}

template<typename T>
const T& Array<T>::operator[](size_t i) const {
	return mData[i];
}
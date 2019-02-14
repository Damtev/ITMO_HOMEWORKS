//
// Created by damtev on 29.11.18.
//

#include "Array.hpp"

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
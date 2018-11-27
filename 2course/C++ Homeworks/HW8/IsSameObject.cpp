//
// Created by damtev on 22.11.18.
//

template <typename T>
bool isSameObject(T const * p, T const * q) {
	return dynamic_cast<const void*>(p) == dynamic_cast<const void*>(q);
}
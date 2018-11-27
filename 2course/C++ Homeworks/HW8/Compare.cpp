//
// Created by damtev on 22.11.18.
//

template <typename T, typename R>
bool compare(T const& t1, T const& t2, R (T::*comparator)) {
	return (t1.*comparator)() < (t2.*comparator)();
}
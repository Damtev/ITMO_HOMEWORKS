//
// Created by damtev on 28.11.18.
//

#include <utility>
#include <cstddef>

template <size_t I1, size_t I2, typename... Args>
auto toPair(const std::tuple<Args...>& tuple) -> decltype(std::make_pair(std::get<I1>(tuple), std::get<I2>(tuple))){
	return std::make_pair(std::get<I1>(tuple), std::get<I2>(tuple));
}
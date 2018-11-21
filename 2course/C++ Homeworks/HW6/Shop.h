//
// Created by damtev on 08.11.18.
//

#ifndef HW6_SHOP_H
#define HW6_SHOP_H

#include <vector>
#include <deque>
#include <ostream>

class Shop {

public:
	explicit Shop(size_t n = 0);
	Shop(const Shop& other);

	~Shop();

	Shop& operator=(const Shop& other);

	// меняют число покупателей
	Shop operator+(unsigned int customers);
	Shop operator-(unsigned int customers);
	Shop& operator+=(unsigned int customers);
	Shop& operator-=(unsigned int customers);

	// меняют количество касс
	Shop operator*(size_t expansion);
	Shop operator/(size_t reduction);
	Shop operator>>(size_t expansion);
	Shop operator<<(size_t reduction);
	Shop& operator*=(size_t expansion);
	Shop& operator/=(size_t reduction);
	Shop& operator>>=(size_t expansion);
	Shop& operator<<=(size_t reduction);

	// возвращает boxes % reduction
	size_t operator%(size_t reduction);
	size_t operator%=(size_t reduction);


	// меняют количество касс
	Shop& operator++();
	Shop& operator--();
	Shop operator++(int);
	Shop operator--(int);

	// | как добавление непустых касс, & создает новый вектор с числом касс boxes & other.boxes
	Shop operator|(const Shop& other);
	Shop operator&(const Shop& other);
	Shop& operator|=(const Shop& other);
	Shop& operator&=(const Shop& other)();

	std::deque<unsigned int>& operator[](int index);

	bool operator==(const Shop& other) const;
	bool operator<(const Shop& other) const;
	bool operator>(const Shop& other);
	bool operator!=(const Shop& other);
	bool operator<=(const Shop& other);
	bool operator>=(const Shop& other);

	// возвращает *this
	Shop& operator,(const Shop& other);

private:
	size_t boxes;
	size_t customers;
	std::vector<std::deque<unsigned int>> cashboxes;

	int compare(const Shop& other);

	friend std::ostream &operator << (std::ostream &out, const Shop& shop);
};

std::ostream& operator<<(std::ostream &out, const Shop& shop);

#endif //HW6_SHOP_H

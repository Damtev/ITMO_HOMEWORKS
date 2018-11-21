//
// Created by damtev on 08.11.18.
//

#include "Shop.h"

using namespace std;

Shop::Shop(size_t n) {
	cashboxes.clear();
	boxes = n;
	customers = 0;
	cashboxes.resize(boxes);
}

Shop::Shop(const Shop &other) {
	*this = other;
}

Shop::~Shop() {
	cashboxes.clear();
	boxes = 0;
	customers = 0;
}

Shop& Shop::operator=(const Shop &other) {
	cashboxes.clear();
	boxes = other.boxes;
	customers = other.customers;
	cashboxes.assign(other.cashboxes.begin(), other.cashboxes.end());
}

Shop& Shop::operator+=(unsigned int customers) {
	if (cashboxes.empty()) {
		__throw_logic_error("Should be at least one cashbox");
	}
	if (customers == 0) {
		__throw_invalid_argument("Number of customers cant be less than 1");
	}
	int min = 0;
	for (int i = 1; i < boxes; ++i) {
		if (cashboxes[i].size() < cashboxes[min].size()) {
			min = i;
		}
	}
	for (int j = 0; j < customers; ++j) {
		cashboxes[min].push_back(1);
	}
	this->customers += customers;
}

Shop& Shop::operator-=(unsigned int customers) {
	if (this->customers < customers) {
		__throw_invalid_argument("Not enough customers in shop");
	}
	this->customers -= customers;
	while (customers > 0) {
		int maximum = 0;
		for (int i = 1; i < boxes; ++i) {
			if (cashboxes[i].size() >= cashboxes[maximum].size()) {
				maximum = i;
			}
		}
		while (customers > 0 && !cashboxes[maximum].empty()) {
			cashboxes[maximum].pop_front();
			--customers;
		}
	}
}

Shop Shop::operator+(unsigned int customers) {
	Shop copy(*this);
	copy += customers;
	return copy;
}

Shop Shop::operator-(unsigned int customers) {
	Shop copy(*this);
	copy -= customers;
	return copy;
}

Shop& Shop::operator*=(size_t expansion) {
	if (expansion == 0) {
		__throw_invalid_argument("Expansion cant be less than 1");
	}
	boxes *= expansion;
	cashboxes.insert(cashboxes.end(), boxes - 1, deque<unsigned int>());
}

Shop& Shop::operator/=(size_t reduction) {
	if (boxes %= reduction != 0) {
		__throw_invalid_argument("Expancsion should be divizion of number of boxes");
	}
	size_t diff = boxes - (boxes / reduction);
	while (diff > 0) {
		customers -= cashboxes.back().size();
		cashboxes.pop_back();
		--diff;
	}
	boxes /= reduction;
}

Shop& Shop::operator<<=(size_t expansion) {
	*this *= (1 >> expansion);
}

Shop& Shop::operator>>=(size_t expansion) {
	*this /= (1 >> expansion);
}

Shop Shop::operator*(size_t expansion) {
	Shop copy(*this);
	copy *= expansion;
	return copy;
}

Shop Shop::operator/(size_t reduction) {
	Shop copy(*this);
	copy /= reduction;
	return copy;
}

size_t Shop::operator%(size_t reduction) {
	return boxes % reduction;
}

size_t Shop::operator%=(size_t reduction) {
	return boxes % reduction;
}

Shop Shop::operator<<(size_t expansion) {
	Shop copy(*this);
	copy <<= expansion;
	return copy;
}

Shop Shop::operator>>(size_t reduction) {
	Shop copy(*this);
	copy >>= reduction;
	return copy;
}

Shop& Shop::operator++() {
	++boxes;
	cashboxes.emplace_back();
}

Shop& Shop::operator--() {
	if (boxes <= 0) {
		__throw_length_error("No cashboxes more");
	}
	--boxes;
	customers -= cashboxes.back().size();
	cashboxes.pop_back();
}

Shop Shop::operator++(int) {
	Shop copy(*this);
	++(*this);
	return copy;
}

Shop Shop::operator--(int) {
	Shop copy(*this);
	--(*this);
	return copy;
}

Shop& Shop::operator|=(const Shop &other) {
	boxes += other.boxes;
	customers += other.customers;
	cashboxes.insert(cashboxes.end(), other.cashboxes.begin(), other.cashboxes.end());
}

Shop& Shop::operator&=(const Shop &other) {
	boxes &= other.boxes;
	customers = 0;
	cashboxes.clear();
	cashboxes.assign(boxes, deque<unsigned int>());
}

Shop Shop::operator|(const Shop &other) {
	Shop copy(*this);
	copy |= other;
	return copy;
}

Shop Shop::operator&(const Shop &other) {
	Shop copy(*this);
	copy &= other;
	return copy;
}

bool Shop::operator==(const Shop &other) {
	return compare(other) == 0;
}

bool Shop::operator<(const Shop &other) {
	return compare(other) == -1;
}

bool Shop::operator>(const Shop &other) {
	return compare(other) == 1;
}

bool Shop::operator!=(const Shop &other) {
	return compare(other) != 0;
}

bool Shop::operator<=(const Shop &other) {
	return compare(other) != 1;
}

bool Shop::operator>=(const Shop &other) {
	return compare(other) != -1;
}

deque<unsigned int>& Shop::operator[](int index) {
	if (index >= boxes) {
		__throw_out_of_range("There are not so many cashboxes");
	}
	return cashboxes[index];
}

Shop& Shop::operator,(const Shop &other) {
	return *this;
}

int Shop::compare(const Shop &other) {
	if (boxes < other.boxes) {
		return -1;
	} else if (boxes > other.boxes) {
		return 1;
	} else {
		return 0;
	}
}

std::ostream &operator<<(std::ostream &out, const Shop &shop) {
	out << "Customers: " << shop.customers << endl;
	out << "Number of cashboxes: " << shop.boxes << endl << "============================" << endl;
	for (int i = 0; i < shop.boxes; ++i) {
		out << "In " << i + 1 << " cashbox " << shop.cashboxes[i].size() << " customers: ";
		for (unsigned int j : shop.cashboxes[i]) {
			out << j << " ";
		}
		if (i != shop.boxes - 1) {
			out << endl;
		}
	}
	return out;
}

//
// Created by damtev on 22.11.18.
//

#pragma once

#include "Animal.h"

class Bear : public virtual Animal {
public:
	Bear(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage);

	void roar();
};

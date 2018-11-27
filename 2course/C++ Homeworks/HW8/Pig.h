//
// Created by damtev on 22.11.18.
//

#pragma once

#include "Animal.h"

class Pig : public virtual Animal {
public:
	Pig(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage);

	void oink();
};

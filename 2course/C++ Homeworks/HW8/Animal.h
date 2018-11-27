//
// Created by damtev on 22.11.18.
//

#pragma once

#include "Unit.h"

class Animal : public virtual Unit {
public:
	Animal(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage);

	void beastHowl();
};
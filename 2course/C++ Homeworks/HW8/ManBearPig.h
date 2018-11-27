//
// Created by damtev on 22.11.18.
//

#pragma once

#include "Bear.h"
#include "Pig.h"
#include "Man.h"

class ManBearPig : public Man, public Bear, public Pig {
public:
	ManBearPig(int maxHp, unsigned int attackPoints, unsigned int defencePoints,
			unsigned int damage, unsigned int healPotion);

	void ultimate(Unit unit);
};

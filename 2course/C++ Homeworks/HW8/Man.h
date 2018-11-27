//
// Created by damtev on 22.11.18.
//

#pragma once

#include "Unit.h"

class Man : public virtual Unit {
public:
	Man(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage, unsigned int healPotion);

	int getHealPotion() const;

	void setHealPotion(unsigned int healPotion);

	void heal();

private:
	unsigned int mHealPotion;
};

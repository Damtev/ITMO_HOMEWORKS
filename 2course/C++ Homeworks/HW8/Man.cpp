//
// Created by damtev on 21.11.18.
//

#include "Man.h"

Man::Man(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage, unsigned int healPotion) :
Unit(maxHp, attackPoints, defencePoints, damage), mHealPotion(healPotion) {}

int Man::getHealPotion() const {
	return mHealPotion;
}

void Man::setHealPotion(unsigned int healPotion) {
	Man::mHealPotion = healPotion;
}

void Man::heal() {
	if (getHp() == getMaxHp()) {
		std::cout << "Max hp are now" << std::endl;
	} else {
		if (mHealPotion > 0) {
			this->setHp(std::min(this->getMaxHp(), this->getHp() + 50));
			--mHealPotion;
		} else {
			std::cout << "No heal potions" << std::endl;
		}
	}
}
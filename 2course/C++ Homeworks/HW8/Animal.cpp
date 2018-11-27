//
// Created by damtev on 21.11.18.
//

#include "Animal.h"

Animal::Animal(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage) :
		Unit(maxHp, attackPoints, defencePoints, damage) {}

void Animal::beastHowl() {
	if (!this->isDead()) {
		this->setAttackPoints(this->getAttackPoints() * 2);
		this->setDefencePoints(this->getDefencePoints() * 2);
	} else {
		std::cout << "Animal is dead";
	}
}
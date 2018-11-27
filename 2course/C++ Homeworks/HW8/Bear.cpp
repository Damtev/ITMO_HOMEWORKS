//
// Created by damtev on 21.11.18.
//

#include "Bear.h"

Bear::Bear(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage) :
Unit(maxHp, attackPoints, defencePoints, damage), Animal(maxHp, attackPoints, defencePoints, damage) {}

void Bear::roar() {
	std::cout << "Roar!" << std::endl;
}
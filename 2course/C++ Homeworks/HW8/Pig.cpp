//
// Created by damtev on 21.11.18.
//

#include "Animal.h"
#include "Pig.h"

Pig::Pig(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage) :
Unit(maxHp, attackPoints, defencePoints, damage), Animal(maxHp, attackPoints, defencePoints, damage) {}

void Pig::oink() {
	std::cout << "Hrew!" << std::endl;
}
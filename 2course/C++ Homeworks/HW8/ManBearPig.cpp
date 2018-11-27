//
// Created by damtev on 21.11.18.
//

#include "ManBearPig.h"

ManBearPig::ManBearPig(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage,
					   unsigned int healPotion) : Unit(maxHp, attackPoints, defencePoints, damage),
					   Animal(maxHp, attackPoints, defencePoints, damage),
					   Man(maxHp, attackPoints, defencePoints, damage, healPotion),
					   Bear(maxHp, attackPoints, defencePoints, damage),
					   Pig(maxHp, attackPoints, defencePoints, damage) {}

void ManBearPig::ultimate(Unit unit) {
	if (!unit.isDead()) {
		unit.setHp(0);
		unit.setDead(true);
	} else {
		std::cout << "Can't kill dead unit";
	}
}

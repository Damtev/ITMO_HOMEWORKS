//
// Created by damtev on 21.11.18.
//

#include "Unit.h"

Unit::Unit(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage) :
mHp(maxHp), mMaxHp(maxHp), mAttackPoints(attackPoints), mDefencePoints(defencePoints), mDamage(damage) {
	if (maxHp <= 0) {
		setDead(true);
	}
}

int Unit::getHp() const {
	return mHp;
}

void Unit::setHp(int mHp) {
	Unit::mHp = mHp;
}

int Unit::getMaxHp() const {
	return mMaxHp;
}

void Unit::setMaxHp(int mMaxHp) {
	Unit::mMaxHp = mMaxHp;
}

unsigned int Unit::getAttackPoints() const {
	return mAttackPoints;
}

void Unit::setAttackPoints(unsigned int mAttackPoints) {
	Unit::mAttackPoints = mAttackPoints;
}

unsigned int Unit::getDefencePoints() const {
	return mDefencePoints;
}

void Unit::setDefencePoints(unsigned int mDefencePoints) {
	Unit::mDefencePoints = mDefencePoints;
}

unsigned int Unit::getDamage() const {
	return mDamage;
}

void Unit::setDamage(unsigned int mDamage) {
	Unit::mDamage = mDamage;
}

bool Unit::isDead() const {
	return mDead;
}

void Unit::setDead(bool mDead) {
	Unit::mDead = mDead;
}

void Unit::attack(Unit& unit) {
	if (!isDead()) {
		if (!unit.isDead()) {
			int diff = getAttackPoints() - unit.getDefencePoints();
			int curDamage = (int) (getDamage() * std::pow(1.05, diff));
			if (getDamage() != 0 && curDamage == 0) {
				curDamage = 1;
			}
			unit.setHp(unit.getHp() - curDamage);
			if (unit.getHp() <= 0) {
				unit.setHp(0);
				unit.setDead(true);
			}
		} else {
			std::cout << "Can't attack dead unit";
		}
	} else {
		std::cout << "Unit is dead";
	}
}
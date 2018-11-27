//
// Created by damtev on 22.11.18.
//

#pragma once

#include <cmath>
#include <iostream>

class Unit {
public:
	int getHp() const;

	void setHp(int hp);

	int getMaxHp() const;

	void setMaxHp(int maxHp);

	unsigned int getAttackPoints() const;

	void setAttackPoints(unsigned int attackPoints);

	unsigned int getDefencePoints() const;

	void setDefencePoints(unsigned int defencePoints);

	unsigned int getDamage() const;

	void setDamage(unsigned int damage);

	bool isDead() const;

	void setDead(bool dead);

	void attack(Unit& unit);

	Unit(int maxHp, unsigned int attackPoints, unsigned int defencePoints, unsigned int damage);

	virtual ~Unit() = default;

private:
	int mHp;
	int mMaxHp;
	unsigned int mAttackPoints;
	unsigned int mDefencePoints;
	unsigned int mDamage;
	bool mDead = false;
};
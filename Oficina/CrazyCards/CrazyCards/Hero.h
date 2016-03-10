#pragma once

#include <iostream>

using namespace std;

class Hero
{
protected:
	Hero(int, int, int);
	virtual int getAttack() { return attack; }
	virtual void setAttack(int attack) { this->attack = attack; }
	virtual int getDefense() { return defense; }
	virtual void setDefense(int defense) { this->defense = defense; }
	virtual int getLife() { return life; }
	virtual void setLife(int life) { this->life = life; }
	~Hero();
private:
	int attack;
	int defense;
	int life;
	int debuffAttack;
	int debuffDefense;
};


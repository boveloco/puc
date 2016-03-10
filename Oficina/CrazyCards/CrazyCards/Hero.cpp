#include "Hero.h"

Hero::Hero(int attack, int defense, int life)
{
	this->attack = attack;
	this->defense = defense;
	this->life = life;
	this->debuffAttack = 0;
	this->debuffDefense = 0;
}

Hero::~Hero()
{
}

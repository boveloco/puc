#include "Hero.h"

Hero::Hero(int attack, int defense)
{
	this->attack = attack;
	this->defense = defense;
}

void Hero::print()
{
	cout << "Hero" << endl;
	cout << "Attack: " << attack << endl;
	cout << "Defense: " << defense << endl;
}

Hero::~Hero()
{
}

#pragma once

#include "CardInstant.h"

class Hero;

class CardInstantAttack : public CardInstant
{
public:
	CardInstantAttack(string, int);
	void print();
	Card* use(Hero *);
	~CardInstantAttack();
private:
	int attack;
};


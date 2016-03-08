#pragma once

#include "Card.h"

enum InstantType {
	INSTANTTYPE_ATTACK,
	INSTANTTYPE_DEFENSE
};

class Hero;

class CardInstant : public Card
{
public:
	CardInstant(string, InstantType);
	virtual void runAction(Hero *) = 0;
	virtual void print();
	~CardInstant();
protected:
	InstantType type;
};


#pragma once

#include "Card.h"

enum actionType {
	INSTANT_TYPE_ATTACK,
	INSTANT_TYPE_DEFENSE
};

class Hero;

class CardInstant : public Card
{
public:
	CardInstant(string, actionType, typeCard);
	virtual Card* use(Hero *) = 0;
	virtual void print();
	~CardInstant();
protected:
	actionType type;
};


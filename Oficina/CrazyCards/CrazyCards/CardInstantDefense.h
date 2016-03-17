#pragma once
#pragma once

#include "CardInstant.h"

class Hero;

class CardInstantDefense : public CardInstant
{
public:
	CardInstantDefense(string, int);
	virtual void print();
	virtual Card* use(Hero *);
	~CardInstantDefense();
private:
	int defense;
};


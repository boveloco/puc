#pragma once
#include "CardEquipment.h"
#include "Hero.h"

#include <vector>

class Unity : public Card, Hero
{
public:
	Unity(string, int, int);
	virtual void print();
	Card* use();
	bool attack(Unity*);
	~Unity();

private:
	CardEquipment* equips;
};

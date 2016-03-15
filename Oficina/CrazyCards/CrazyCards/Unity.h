#pragma once
#include "Card.h"
#include "Hero.h"
#include "CardEquipment.h"
#include "Hero.h"
#include <vector>

class Unity : public Card, Hero
{
public:
	Unity(string, int, int);
	virtual void print();
	void attack();
	~Unity();

private:
	CardEquipment* equips;
};

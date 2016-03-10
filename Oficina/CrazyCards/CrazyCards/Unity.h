#pragma once
#include "Card.h"
#include "Hero.h"
#include "CardEquipment.h"
#include <vector>

class Unit : public Card, Hero
{
public:
	Unit(string, int, int);
	virtual void print();
	void attack();
	~Unit();

private:
	CardEquipment equips;
};

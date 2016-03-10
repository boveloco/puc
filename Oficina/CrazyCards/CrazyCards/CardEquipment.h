#pragma once
#include "Card.h"

class CardEquipment : public Card
{
public:
	CardEquipment(string);
	~CardEquipment();

	virtual void print();
	bool use();
private:
	int duration;

};


#pragma once
#include "Card.h"

class CardEquipment : public Card
{
public:
	CardEquipment();
	~CardEquipment();

	void buff(int, int);
	bool use();
private:
	int duration;

};


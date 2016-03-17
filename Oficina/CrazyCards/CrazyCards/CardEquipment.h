#pragma once
#include "Card.h"
class Unity;

class CardEquipment : public Card
{
public:
	CardEquipment(string, int, int);
	~CardEquipment();

	virtual void print();
	Card* use();
	int getLife() const;
	void setLife(int);
private:
	int duration;
	int life;
	Unity * cardBuffed;
};


#include "CardInstantDefense.h"

CardInstantDefense::CardInstantDefense(string title, int defense)
	: CardInstant(title, INSTANT_TYPE_DEFENSE, INSTANT_CARD)
{
	this->defense = defense;
}

void CardInstantDefense::print()
{
	CardInstant::print();
	cout << "Defense Bonus: " << this->defense << endl;

}

void CardInstantDefense::runAction(Hero *)
{
	//TODO remove ataque do target durante um turno
}

CardInstantDefense::~CardInstantDefense()
{	
}

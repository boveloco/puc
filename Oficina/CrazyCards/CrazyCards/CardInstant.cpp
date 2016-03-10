#include "CardInstant.h"

CardInstant::CardInstant(string title, actionType actionType, typeCard cardType)
	:Card(title, cardType)
{
	this->type = actionType;
}

void CardInstant::print()
{
	cout << "Carta: " << title << endl;
}

CardInstant::~CardInstant()
{
}

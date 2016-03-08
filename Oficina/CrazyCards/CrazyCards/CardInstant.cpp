#include "CardInstant.h"

CardInstant::CardInstant(string title, InstantType type)
	:Card(title)
{
	this->type = type;
}

void CardInstant::print()
{
	cout << "Carta: " << title << endl;
}

CardInstant::~CardInstant()
{
}

#include "Card.h"
#include "CardInstantAttack.h"
CardInstantAttack::CardInstantAttack(string title, int attack)
	:CardInstant(title, INSTANT_TYPE_ATTACK, INSTANT_CARD)
{
	this->attack = attack;
}

void CardInstantAttack::print()
{
	CardInstant::print();
	cout << "Attack Bonus: " << attack << endl;
}

Card* CardInstantAttack::use(Hero *hero)
{
	//TODO: remove ataque do target durante um turno
	return nullptr;
}

CardInstantAttack::~CardInstantAttack()
{
}

#include "CardInstantAttack.h"
#include "Hero.h"

CardInstantAttack::CardInstantAttack(string title, InstantType type, int attack)
	:CardInstant(title, type)
{
	this->attack = attack;
}

void CardInstantAttack::print()
{
	CardInstant::print();
	cout << "Attack Bonus: " << attack << endl;
}

void CardInstantAttack::runAction(Hero *hero)
{
	hero->setAttack(hero->getAttack() + attack);
}

CardInstantAttack::~CardInstantAttack()
{
}

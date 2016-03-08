#include <iostream>
#include "CardInstantAttack.h"
#include "Hero.h"

int main(int argc, char *argv) {
	
	Hero hero(10, 20);
	hero.print();

	CardInstantAttack *cia = new CardInstantAttack("Motivation", INSTANTTYPE_ATTACK, 3);
	cia->runAction(&hero);
	cia->print();
	hero.print();

	CardInstant *instant = cia;
	instant->print();

	Card *card = cia;
	card->print();
}
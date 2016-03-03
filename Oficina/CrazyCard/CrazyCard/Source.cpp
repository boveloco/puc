#include <iostream>
#include <string>
#include "Hero.h"
#include "CardInstantAttack.h"
int main(int argc, char *argv) {
	
	Hero h(10, 20);
	h.print();

	CardInstantAttack *cia = new CardInstantAttack("Motivation", INSTANTTYPE_ATTACK, 3);
	cia->print();
	cia->runAction(&h);
	h.print();

	CardInstant *instant = cia;
	instant->print();

	Card *card = cia;
	card->print();

	system("PAUSE");
	return 0;
}
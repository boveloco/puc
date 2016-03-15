#include "CardEquipment.h"

CardEquipment::CardEquipment(string title, int duration, int life)
	: Card(title, EQUIPMENT_CARD)
{
	this->life = life;
	this->duration = duration;
	this->cardBuffed = nullptr;
}

CardEquipment::~CardEquipment()
{
	delete this->cardBuffed;
}

void CardEquipment::print()
{
	std::cout << title << " Aumenta sua vida em : " << this->getLife() << std::endl;
}

void CardEquipment::setLife(int life) {
	this->life = life;
}
int CardEquipment::getLife() const{
	return this->life;
}
bool CardEquipment::use()
{
	//TODO: mim faiz moçu
	return false;
}

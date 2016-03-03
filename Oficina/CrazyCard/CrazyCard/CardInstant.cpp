#include "CardInstant.h"
#include "Hero.h"
CardInstant::CardInstant(string title, InstantType type) : Card(title){
	this->type = type;
}

void CardInstant::print(){
	cout << "Carta: " << this->title << endl;
}

CardInstant::~CardInstant(){}



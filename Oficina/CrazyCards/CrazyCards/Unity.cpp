#include "Unity.h"

Unit::Unit(string title, int attack, int defense):
	Card::Card(title, UNITY_CARD),
	Hero::Hero(attack, defense)
{

}

void Unit::print()
{
	std::cout << "Meu nome e: " 
			  << title
			  << " e meu ataque e: " 
			  << this->getAttack() 
			  << " e minha defesa e: " 
		      << this->getDefense() 
		      << std::endl;
}

Unit::~Unit()
{
}


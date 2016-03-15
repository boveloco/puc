#include "Unity.h"

Unity::Unity(string title, int attack, int defense)
	: Card(title, UNITY_CARD),
	  Hero(attack, defense, 3)
{

}

void Unity::print()
{
	std::cout << "Meu nome e: " 
			  << title
			  << " e meu ataque e: " 
			  << this->getAttack() 
			  << " e minha defesa e: " 
		      << this->getDefense() 
		      << std::endl;
}

void Unity::attack()
{
	//TODO: mim faiz
}

Unity::~Unity()
{
}


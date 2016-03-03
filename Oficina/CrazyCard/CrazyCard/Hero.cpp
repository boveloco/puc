#include "Hero.h"
#include <iostream>

using namespace std;

Hero::Hero() {}

Hero::~Hero(){}

Hero::Hero(int attack, int defense) {
	this->attack = attack;
	this->defense = defense;
}

void Hero::print() {
	cout << "hero: " << endl;
	cout << "Attack: " << attack << endl;
	cout << "Defense: " << defense << endl;
}
#pragma once

#include <iostream>

using namespace std;

class Hero
{
public:
	Hero(int, int);
	void print();
	int getAttack() { return attack; }
	void setAttack(int attack) { this->attack = attack; }
	int getDefense() { return defense; }
	void setDefense(int defense) { this->defense = defense; }
	~Hero();
private:
	int attack;
	int defense;
	Card* buff;
};


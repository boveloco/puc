#pragma once

class Hero
{
public: 
	Hero();
	Hero(int defense, int attack);

	int getAttack() { return attack; }
	void setAttack(int attack) { this->attack = attack; }
	int getDefense() { return defense; }
	void setDefense(int defense) { this->defense = defense; }
	void print();
	~Hero();
private:
	int attack;
	int defense;
};
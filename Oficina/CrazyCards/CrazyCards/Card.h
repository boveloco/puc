#pragma once

#include <string>
#include <iostream>

using namespace std;
enum typeCard {
	INSTANT_ATTACK_CARD,
	INSTANT_DEFENSE_CARD,
	UNITY_CARD,
	EQUIPMENT_CARD
};

class Card
{
public:
	Card(string);
	virtual void print() = 0;
	~Card();
	typeCard type;
protected:
	string title;
};


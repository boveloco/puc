#pragma once

#include <string>
#include <iostream>

using namespace std;
enum typeCard {
	INSTANT_CARD,
	UNITY_CARD,
	EQUIPMENT_CARD
};

class Card
{
public:
	Card(string, typeCard);
	virtual void print() = 0;
	~Card();
	typeCard type;
protected:
	string title;
};


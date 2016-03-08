#pragma once

#include "Card.h"
#include <vector>

using namespace std;
class Deck
{
public:
	//construtor numero de cartas
	Deck(int);
	~Deck();

private:
	vector<Card *> &deck;

	//embaralhar deck
	void shuffle();

	//remove carta
	void remove(int n);

	//comprar carta
	Card* draw();

};
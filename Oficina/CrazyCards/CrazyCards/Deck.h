#pragma once

#include <vector>

class Card;

using namespace std;
class Deck
{
public:
	//construtor numero de cartas
	Deck(int);
	~Deck();

	//comprar carta
	Card* draw();

private:
	vector<Card *> deck;

	//embaralhar deck
	void shuffle();
	//remove carta
	void remove(int n);
};
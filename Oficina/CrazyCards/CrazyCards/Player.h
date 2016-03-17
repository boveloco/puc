#pragma once
#include "Deck.h"


#include <vector>


class Player
{
public:
	Player(int);
	void draw(int);
	void discard(Card*, int);
	Card* use(Card*);
	~Player();
	
private:
	std::vector<Card*> hand;
	std::vector<Card*> graveyard;
	Deck* deck;
};
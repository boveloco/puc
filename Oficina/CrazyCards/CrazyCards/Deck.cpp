#include <algorithm>
#include <time.h>
#include "CardInstant.h"
#include "CardInstantAttack.h"
#include "Deck.h"

void Deck::shuffle()
{
	std::random_shuffle(this->deck.begin(), this->deck.end());
	std::cout << "Embaralhou" << std::endl;
}

void Deck::remove(int n)
{
	delete this->deck[n];
	this->deck.erase[n];
}

Card* Deck::draw()
{
	if(this->deck.size()){

		Card* n = this->deck[0];
		this->remove(0);
		return n;
	}
	
	return nullptr;
}

Deck::Deck(int n) {
	srand(time(NULL));

	for (size_t i = 0; i < n; i++){
		switch (rand() % 3)
		{
		case typeCard::INSTANT_CARD:
			this->deck.push_back(new CardInstantAttack("Instant Attack", 10));

			break;


		default:
			break;
		}
		this->deck.push_back()
	}
}

Deck::~Deck() {
	for (size_t i = 0; i < this->deck.size(); i++)
	{
		delete this->deck[i];
	}
	this->deck.clear();
}

#include "Player.h"
#include "CardInstantAttack.h"

Player::Player(int n)
{
	this->deck = new Deck(n);
}

void Player::draw(int n)
{
	if (n < 1)
		return;

	for (size_t i = 0; i < n; i++)
	{
		this->hand.push_back(this->deck->draw());
	}
}

void Player::discard(Card* card, int n)
{
	this->graveyard.push_back(card);
	this->hand.erase(this->hand.begin() + n);
}

Card* Player::use(Card * card)
{
	return card->use();

}

Player::~Player()
{
}

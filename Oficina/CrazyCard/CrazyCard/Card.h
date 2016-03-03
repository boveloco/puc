#pragma once
#include <string>
#include <iostream>
using namespace std;

class Card
{
public:
	Card(string);
	virtual void print() = 0;
	~Card();
protected:
	string title;
};

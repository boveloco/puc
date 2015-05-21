#pragma once
#include "Array.h"
#include "SelectionSort.h"
#include "Inimigos.h"
#include <iostream>

int compararEnergiaInimigos(Inimigos a, Inimigos b);
int main(){

	Array<Inimigos> numbs(5);

	numbs[0].energia = 13;
	numbs[1].energia = 30;
	numbs[2].energia = 20;
	numbs[3].energia = 10;
	numbs[4].energia = 5;

	selectionSort<Inimigos>(numbs, compararEnergiaInimigos);

	for (int i = 0; i < numbs.Size(); i++){
		std::cout << "Numb: " << i << " " << numbs[i].energia << std::endl;
	}

	system("PAUSE");
	return 1;
}
int compararEnergiaInimigos(Inimigos a, Inimigos b){

	if (a.energia > b.energia)
		return 1;
	else
		return 0;
}
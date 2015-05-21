#include "insertSort.h"
#include "Array.h"
#include "Inimigos.h"
#include <iostream>

int compararEnergiaInimigos(Inimigos a, Inimigos b);

int main(){
	
	Array<Inimigos> numbs(5);

	numbs[0].energia = 30;
	numbs[1].energia = 20;
	numbs[2].energia = 10;
	numbs[3].energia = 50;
	numbs[4].energia = 43;
	InsertSort<Inimigos>(numbs, compararEnergiaInimigos);

	for (size_t i = 0; i < numbs.Size(); i++){
		std::cout << "Nro :" << i << " = " << numbs[i].energia << std::endl;
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
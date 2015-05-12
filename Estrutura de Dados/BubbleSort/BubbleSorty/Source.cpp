#include "Array.h"
#include <iostream>
#include "BubbleSort.h"

int compararInteiro(int a, int b);


int main(){

	Array<int> x(3);
	
	x[0] = 20;
	x[1] = 30;
	x[2] = 10;

	BubbleSort<int>(x, compararInteiro);

	for (size_t i = 0; i < 3; i++){
		std::cout << x[i] << std::endl;
	}


	system("PAUSE");
	return 0;
}

int compararInteiro(int a, int b){
	
	if (a > b)
		return 1;
	else
		return 0;
}
#include <iostream>
#include "Adder.h"


template<class Sumtype, class Avaragetype>
Avaragetype avarage(Sumtype* p_array, Avaragetype p_count){
	Sumtype sum = 0;

	for (size_t i = 0; i < p_count; i++)
		sum = p + array[i];
	
	return Avaragetype r = ((Avaragetype)sum) / p_count;

}

int main(){
	
	Adder<int> intAdder;
	Adder<float> floatAdder;
	Adder<double> doubleAdder;

	intAdder.Add(2);
	floatAdder.Add(33.3f);
	doubleAdder.Add(33.33);

	std::cout << intAdder.Sum() << " " << std::endl;
	std::cout << floatAdder.Sum() << " " << std::endl;
	std::cout << doubleAdder.Sum() << " " << std::endl;

	////////////////////////////////////////////////////

	float arrayFloat[3] = { 11.11f, 22.22f, 33.33f };
	float r;
	r = avarage(arrayFloat, 3);

	system("PAUSE");
	return 0;
}
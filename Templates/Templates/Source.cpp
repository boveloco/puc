#include <iostream>

int SumIntegers(int* p_array, int p_count){
	int total = 0;
	for (int i = 0; i < p_count; i++)
		total += p_array[i];

	return total;
}
float SumFloat(float* p_array, int p_count){

	float total = 0;
	for (size_t i = 0; i < p_count; i++){
		total += p_array[i];
	}

	return total;
}

template <class T>
T Sum(T* p_array, int p_count){
	T total = 0;
	for (size_t i = 0; i < p_count; i++)
		total += p_array[i];

	return total;
}

int main(){
	int array_de_int[3] = { 10, 20, 30 };
	float array_de_float[3] = { 11.11f, 22.22f, 33.33f };

	//int r1 = SumIntegers(array_de_int, 3);
	//float r2 = SumFloat(array_de_float, 3);

	int t1 = Sum<int>(array_de_int, 3);
	float t2 = Sum<float>(array_de_float, 3);
	std::cout << "Soma dos interios: " << t1 << std::endl;
	std::cout << "Soma dos floats: " << t2 << std::endl;

	system("PAUSE");

	return 0;
}
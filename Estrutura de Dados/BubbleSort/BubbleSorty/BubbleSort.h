#pragma once
#include "Array.h"

template<class T>
void BubbleSort(Array<T>& p_array, int(*p_compare)(T,T)){

	int top = p_array.Size() -1;
	int index;
	int swaps = 1;

	while (top!= 0 && swaps != 0){

		swaps = 0;

		for (index = 0; index < top; index++){
			if (p_compare(p_array[index], p_array[index + 1]) > 0){

				T tmp = p_array[index];
				p_array[index] = p_array[index + 1];
				p_array[index + 1] = tmp;
				swaps++;
			}
		}

		top--;
	}
}
#pragma once
#include "Array.h"

template<class T>
void InsertSort(Array<T>& p_array, int(*p_compare)(T, T)){

	int top = p_array.Size() - 1;
	int i, j;

	for (i = 1; i < p_array.Size(); i++){
		T flag = p_array[i];

		j = i - 1;
		while (j >= 0 && p_compare(p_array[j], flag) > 0){
			p_array[j + 1] = p_array[j];
			j--;
		}
		p_array[j + 1] = flag;
	}
}
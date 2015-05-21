#pragma once
#include "Array.h"

template<class T>
void selectionSort(Array<T>& p_array, int(*p_compare)(T, T)){

	T flag;
	int i, j, flagI;
	int n = 0;

	
	for (i = 0; i < p_array.Size(); i++){
		flag = p_array[i];
		flagI = i;
		for (j = i + 1; j < p_array.Size(); j++){
			if (p_compare(flag, p_array[j]) > 0){
				flag = p_array[j];
				flagI = j;
			}
		}
		if (flagI != i){
			T temp = p_array[i];
			p_array[i] = flag;
			p_array[flagI] = temp;
		}
	}

}
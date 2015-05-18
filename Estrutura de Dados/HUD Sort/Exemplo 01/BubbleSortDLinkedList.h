#pragma once
#include "DLinkedList.h"

template<class T>
void BubbleSort(DLinkedList<T>& p_list, int(*p_compare)(T, T)){

	int top = p_list.Size() - 1;
	int index;
	int swaps = 1;

	while (top!= 0 && swaps != 0){
		
		swaps = 0;

		for (index = 0; index < top; index++){
			if (p_compare(p_list.GetIterator() + index, p_list.GetIterator() + index + 1)){
				p_list.InsertBefore(p_list.GetIterator() +index)
			}
		}
	}
}
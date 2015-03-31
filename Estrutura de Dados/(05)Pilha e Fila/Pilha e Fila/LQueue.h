#pragma once
#include "DLinkedList.h"
template<class T>
class LQueue : public DLinkedList<T>{
public:
	void Enqueue(T p_data){
		Append(p_data);
	}
	void Dequeue(){
		RemoveHead();
	}
	T Front(){
		return m_head->m_data;
	}
	int Count(){
		return m_count;
	}
};
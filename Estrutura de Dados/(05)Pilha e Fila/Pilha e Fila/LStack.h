#include "DLinkedList.h"


#pragma once
template<class T>
class LStack : public DLinkedList<T>{
	public:
		void push(T p_data){
			Append(p_data);
		}
		void Pop(){
			RemoveTail();
		}

		T Top(){
			return m_tail->m_data;
		}
		int Count(){
			return m_count;
		}
};
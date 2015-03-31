#pragma once

#include "Array.h"

template<class T>
class AStack : public Array < T > {

public:
	int m_top;
	AStack(int p_size) : Array(p_size){
		m_top = 0;
	}
	bool push(T p_data){
		if (m_size != m_top){
			m_array[m_top] = p_data;
			m_top++;
			return true;
		}
		return false;
	}
	void Pop(){
		if (m_top > 0)
			m_top--;
	}

	T Top(){
		return m_array[m_top - 1];
	}

	int Count(){
		return m_top;
	}

};
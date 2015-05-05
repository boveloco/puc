#pragma once
#include "BinaryTree.h"

template<class T>
class BinarySeachTree{
public:
	typedef BinaryTree<T> Node;
	Node* m_root;

	int(*m_compare)(T, T);

	BinarySeachTree(int(*p_compare)(T, T)){
		m_root = nullptr;
		m_compare = p_compare;
	}
	~BinarySeachTree(){
		if (m_root != nullptr)
			delete m_root;
	}

	void insert(T p_data){
		Node* current = m_root;
		if (m_root == nullptr)
			m_root = new Node(p_data);
		else{
			while (current != nullptr){
				if (m_compare(p_data, current->m_data) < 0){
					if (current->m_left == nullptr){
						current->m_left = new Node(p_data);
						current->m_left->m_parent = current;
						current = nullptr;
					}
					else{
						current = current->m_left;
					}
				}
				else {
					if (current->m_right == nullptr){
						current->m_right = new Node(p_data);
						current->m_right->m_parent = current;
						current = nullptr;
					}
					else {
						current = current->m_right;
					}
				}
			}
		}
	}

	Node* Find(T p_data){
		Node* current = m_root;

		int temp;
		while (current != nullptr)
		{
			temp = m_compare(p_data, current->m_data);
			if (temp == 0)
				return current;
			if (temp < 0)
				current = current->m_left;
			else current = current->m_right;
		}
		return 0;
	}
};
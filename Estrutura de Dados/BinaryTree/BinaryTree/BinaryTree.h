#pragma once

void Destroy();
template<class T>
class BinaryTree{
public:
	typedef BinaryTree<T> Node;

	Node* m_left;
	Node* m_right;
	Node* m_parent;
	T m_data;



	BinaryTree(){
		m_parent = 0;
		m_right = 0;
		m_left = 0;
	}

	~BinaryTree(){
		Destroy();
	}

	void Destroy(){
		{
			if (m_left)
				delete m_left;
			m_left = 0;

			if (m_right)
				delete m_right;
			m_right = 0;
		}
	}
	
	int Count(){
		int c = 1;

		if (m_left)
			c += m_left->Count();

		if (m_right)
			c += m_right->Count();

		return c;
	}

	void Preorder(Node* node){
		std::cout << node->m_data << std::endl;
		if (node->m_left)
			Preorder(node->m_left);
		if (node->m_right)
			Preorder(node->m_right);
	}
	void Preorder(){
		Preorder(this);
	}
	void PosOrder(node* node){
		if (node->m_left)
			Preorder(node->m_left);
		if (node->m_right)
			Preorder(node->m_right);
		std::cout << node->m_data << std::endl;
	}
	void PosOrder(){
		PosOrder(this);
	}
};
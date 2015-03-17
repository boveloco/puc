#ifndef __SLINKEDLIST_H_
#define __SLINKEDLIST_H_

template<class T>
class SListNode{

public:
	T m_data;
	SListNode<T> * m_next;

	SListNode(){
		m_next = 0;
	}

	void InsertAfter(T p_data){
		SLinkedNode<T>* newNode;
		newNode = new SListNode();
		newNode->m_data = p_data;
	}

#endif
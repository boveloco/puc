#include "SLinkedList.h"
#include <iostream>
template<class T>
void printList(SLinkedList<T>* list){
	printList(list->getHead());
}

template<class T>
void printList(SListNode<T>* node){
	std::cout << node->getData() << std::endl;
	if (node->getNext() != 0)
		printList(node->getNext());
}

int main(){
	SLinkedList<int> lista;
	lista.Append(99);
	lista.Append(98);
	lista.Append(97);

	SListNode<int>* node = new SListNode<int>();
	node->setData(10);
	
	node->setNext(new SListNode<int>());
	node->getNext()->setData(20);
	
	node->getNext()->setNext(new SListNode<int>());
	node->getNext()->getNext()->setData(30);
	
	//do começo ao fim
	SListNode<int>* itr = node;
	printList(itr);
	//print Recursivo

	SListNode<char>* node2 = new SListNode<char>();
	node2->setData('a');
	node2->InsertAfter('b');
	node2->InsertAfter('c');
	//print recursiva
	printList(node2);

	SLinkedList<int>* lista2 = new SLinkedList<int>();
	lista2->Append(10);
	lista2->Append(20);
	lista2->Append(30);
	//print recursiva
	printList(lista2);

	SLinkedList<int>* list3 = new SLinkedList<int>();
	list3->Append(50);
	list3->Append(60);
	list3->Append(70);
	SListIterator<int> itr2 = list3->GetIterator();

	for (itr2.Start(); itr2.Valid(); itr2.Forth()){
		std::cout << itr2.Item() << std::endl;
	}


	system("PAUSE");
	return 0;
}


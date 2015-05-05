#include "BinarySearchTree.h"
#include <iostream>

int compararInteiro(int p1, int p2);
int main(){

	BinarySeachTree<int> tree(compararInteiro);

	BinarySeachTree<int>* node;
	tree.insert(8);
	tree.insert(4);
	tree.insert(12);
	tree.insert(6);
	tree.insert(10);
	tree.insert(14);

	std::cout << "Procurando 10" << std::endl;
	tree.Find(10);

	system("PAUSE");
	return 0;
}

int compararInteiro(int p1, int p2){
	std::cout << " p1: " << p1
			  << " p2: " << p2
			  << std::endl;
	return p1 - p2;
}
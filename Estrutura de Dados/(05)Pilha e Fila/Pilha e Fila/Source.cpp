#include "LStack.h"
#include "AStack.h"
#include <iostream>

int main(){

	std::cout << "Testando o metodo POP" << std::endl;

	LStack<int> p1;
	for (size_t i = 0; i < 3; i++)
		p1.push(i);

	p1.Pop();
	p1.Pop();

	std::cout << p1.Top() << std::endl;

	std::cout << "Tamanho depois do pop: " << p1.Count() << std::endl;
	p1.Pop();
	std::cout << "Tamanho depois do 2 pop: " << p1.Count() << std::endl;

	AStack<char> p2(3);
	p2.push('a');
	p2.push('b');
	p2.push('c');

	system("PAUSE");
	return 1;
}
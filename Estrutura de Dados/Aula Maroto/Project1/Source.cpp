#include <list>
#include <iostream>
using namespace std;

void imprimirList(list<int> &p_lst);

int main(){

	list<int> lst; 

	lst.push_back(6);
	lst.push_back(1);
	lst.push_back(5);
	lst.push_back(3);
	lst.push_back(9);

	imprimirList(lst);

	lst.pop_back();

	imprimirList(lst);

	lst.pop_front();

	imprimirList(lst);

	lst.sort();

	imprimirList(lst);

	lst.reverse();

	imprimirList(lst);

	lst.push_front(17);

	imprimirList(lst);

	system("pause");
	return 0;
}

void imprimirList(list<int> &p_lst){
	for (list<int>::iterator itr = p_lst.begin(); itr != p_lst.end(); itr++){
		cout << *itr << " - ";
	}
	cout << endl;
}
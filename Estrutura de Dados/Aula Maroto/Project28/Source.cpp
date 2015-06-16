#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void mostrarVector(vector<int> &p_vet);

int main(){
	vector<int> vet;
	
	vet.push_back(7);
	vet.push_back(2);
	vet.push_back(8);
	vet.push_back(5);
	vet.push_back(9);
	vet.push_back(4);
	vet.push_back(3);
	vet.push_back(1);
	vet.push_back(6);

	mostrarVector(vet);

	sort(vet.begin(), vet.end() -3);

	mostrarVector(vet);

	system("pause");
	return 0;
}

void mostrarVector(vector<int> &p_vet){

	for (vector<int>::iterator itr = p_vet.begin(); itr != p_vet.end(); itr++)
		cout << *itr << " - ";

	cout << endl;

}
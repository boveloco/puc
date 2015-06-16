#include<iostream>
#include<map>
#include<string>

using namespace std;

void mostrarDados();

int main(){


	map<const char*, int> personagem;
	personagem["vida"]	= 100;
	personagem["posx"]	= 0;
	personagem["posy"]	= 1;

	string s;

	cin >> s;
	 
	cout << personagem[s.c_str()] << endl;

	system("pause");
	return 0;
}
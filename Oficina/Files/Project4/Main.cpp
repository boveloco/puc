#include <iostream>
#include "FileMananger.h"

int main(int argv, char *argc) {
	FileMananger *wFile = new FileMananger();
	wFile->writeLine("Primeira linha");
	wFile->writeLine("Segunda linha");
	wFile->writeLine("Terceira linha");
	wFile->writeFile("Jovem.txt");

	FileMananger *iFile = new FileMananger();
	int lines = iFile->readFile("Jovem.txt");
	for (int i = 0; i < lines; i++)	{
		string line = iFile->readLine(i);
		cout << iFile->readLine(i) << endl;
	}

	system("PAUSE");
}
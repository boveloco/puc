#pragma once

#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

class FileMananger
{
private:
	std::ifstream				inFile;
	std::ofstream				outFile;
	std::vector<std::string>	text;
public:
	FileMananger();
	~FileMananger();
	
	//IO Functions
	int readFile(std::string);
	std::string readLine(int);
	bool writeFile(std::string);
	void writeLine(std::string);
};


#include "FileMananger.h"



FileMananger::FileMananger(){
}


FileMananger::~FileMananger(){
}

int FileMananger::readFile(std::string path){
	std::string line;
	inFile.open(path, ios::in);
	if (inFile.is_open()) {
		while (getline(inFile, line))
			text.push_back(line);
		inFile.close();
		return text.size();
	}
	else
		return -1;
}

std::string FileMananger::readLine(int line){
	if (line < text.size()) {
		return text[line];
	}
	return std::string();
}

bool FileMananger::writeFile(std::string path){
	outFile.open(path, ios::out);
	if (outFile.is_open()) {
		for (string t : text) {
			outFile << t << endl;
		}
		outFile.close();
		return true;
	}
	else
		return false;
}

void FileMananger::writeLine(std::string line){
	text.push_back(line);
}

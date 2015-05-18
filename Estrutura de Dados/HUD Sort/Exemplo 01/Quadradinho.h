#include <stdlib.h>
#include <time.h>
#include <c2d2\chien2d2.h>
//random array
int random(int size, int add);

class Quadradinho{

public:
	Quadradinho(){}

	Quadradinho(int x, int y, int c){
		pos_x = random(x, 0);
		pos_y = random(y, 0);
		color = random(c, 1);
	}

	int random(int size, int add){
		return rand() % size + add;
	}

	int getPosX(){
		return pos_x;
	}
	int getPosY(){
		return pos_y;
	}
	int getColor(){
		return color;
	}
	void setPosX(int n){
		pos_x = n;
	}
	void setPosY(int n){
		pos_y = n;
	}
	void setColor(int n){
		color = n;
	}
private:
	int pos_x;
	int pos_y;
	int color;

};

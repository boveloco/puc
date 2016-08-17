#include "ofMain.h"
#include "ofApp.h"
#include "../Rafagan/floating_points.h"

//========================================================================
int main( ){
	//ofSetupOpenGL(1024,768,OF_WINDOW);			// <-------- setup the GL context

	// this kicks off the running of my app
	// can be OF_WINDOW or OF_FULLSCREEN
	// pass in width and height too:
	//ofRunApp(new ofApp());
	
	double doubl = 0;

	intBitsToFloat(10);

	std::cout << doubl << std::endl;
	//0.125;
	//11001; //25
	//1111101; //125
	//2 ^ 7;
	//111; //7 exp
	//0; // sinal
	//01111111101; //nro com tudo

	std::cout << intBitsToFloat(0b01111111101) << std::endl;
	system("PAUSE");
}

#include <iostream>



void mae(float a, float b, float c) {

	std::cout << (a / b) * c << std::endl;
	std::cout << (a * c) / b << std::endl;
}

bool diff(float a, float b, float ep) {
	if (abs(a-b) <= ep) {
		return true;
	}
	return false;
}

float ep() {
	float machEps = 1.0f;

	do
		machEps /= 2.0f;
	while ((float)(1.0 + (machEps / 2.0)) != 1.0);
	return machEps;

}

int main() {
	for (auto n = 0; n <= 156424; n++) {
		std::cout << ep() << std::endl;	
	}
	bool n = diff(13.1231f, 42.243f, ep());
	std::cout << n << std::endl;
	mae(7.9f, 3.0f, 1700000.0f);

	system("PAUSE");
	return 0;
}
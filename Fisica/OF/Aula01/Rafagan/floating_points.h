#include <stdio.h>
#include <memory.h>
#include <stdint.h>

union int_float_bits {
	int32_t int_bits;
	float float_bits;
};

union int_double_bits {
	int64_t int_bits;
	double double_bits;
};

float intBitsToFloat(int32_t x)
{
	union int_float_bits bits;
	bits.int_bits = x;
	return bits.float_bits;
}

double longIntBitsToDouble(int64_t x)
{
	union int_double_bits bits;
	bits.int_bits = x;
	return bits.double_bits;
}

/*int main() {
	printf("%f\n", intBitsToFloat(0b11000001000100000000000000000000));
	printf("%f\n", longIntBitsToDouble(0b1100000000100010000000000000000000000000000000000000000000000000L));
}*/

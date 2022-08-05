#include <stdio.h>

int main(int argv, char *argc[]){
	int x, y, z;
	x = 1;
	y = 2;
	z = 3;
	double dx = (double) x;
	double dy = (double) y;
	double dz = (double) z;
	printf("x = %d, dx = %f\n", x, dx);
	printf("y = %d, dy = %f\n", y, dy);
	printf("z = %d, dz = %f\n", z, dz);
	printf("dx*dx >= 0 is %d\n", (dx*dx >= 0));
	printf("(double)(float) x == dx is %d\n", ((double)(float) x == dx));
	printf("dx+dy == (double) (x+y) is %d\n", (dx+dy == (double) (x+y)));
	printf("(dx + dy) + dz == dx + (dy + dz) is %d\n", ((dx+dy)+dz == dx+(dy+dz)));
	printf("dx*dy*dz == dz*dy*dx is %d\n", (dx*dy*dz == dz*dy*dx));
	printf("dx/dx == dy/dy is %d\n", (dx/dx == dy/dy));
	return 0;
}

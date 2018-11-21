#include <iostream>
#include <vector>
#include "Shop.h"

using namespace std;

int main() {
	Shop shop(5);
	for (int i = 1; i <= 20; ++i) {
		shop += i;
	}

	--shop;
	cout << shop << endl;

	return 0;
}
//
// Created by damtev on 29.12.18.
//

#include <algorithm>
#include <iostream>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	int n, k;
	cin >> n >> k;
	--k;
	int array[n];
	int A, B, C, first, second;
	cin >> A >> B >> C >> first >> second;
	array[0] = first;
	array[1] = second;
	for (int i = 2; i < n; ++i) {
		int cur = A * first + B * second + C;
		array[i] = cur;
		first = second;
		second = cur;
	}
	nth_element(array, array + k, array + n);
	cout << array[k];
	return 0;
}
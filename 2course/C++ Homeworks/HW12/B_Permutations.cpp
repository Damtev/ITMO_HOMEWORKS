//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <algorithm>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	int n;
	cin >> n;
	int permutations[n];
	for (int i = 0; i < n; ++i) {
		permutations[i] = i + 1;
	}
	do {
		for (int i = 0; i < n; ++i) {
			cout << permutations[i] << " ";
		}
		cout << "\n";
	} while (next_permutation(permutations, permutations + n));
	return 0;
}
//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <unordered_set>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	boolalpha(cout);
	unordered_set<int> set;
	char command[18];
	int key;
	while (cin.getline(command, 18)) {
		key = atoi(&command[7]);
		char firstSymbol = command[0];
		if (firstSymbol == 'i') {
			set.insert(key);
		} else if (firstSymbol == 'e') {
			cout << (bool) set.count(key) << "\n";
		} else {
			set.erase(key);
		}
	}
	return 0;
}
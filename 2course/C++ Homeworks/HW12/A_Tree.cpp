#include <iostream>
#include <set>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	boolalpha(cout);
	cin.tie(nullptr);
	set<int> tree;
	string command;
	int key;
	while (cin >> command >> key) {
		const char firstSymbol = command[0];
		switch (firstSymbol) {
			case 'i': {
				tree.insert(key);
				break;
			}
			case 'e': {
				cout << (tree.find(key) != tree.end()) << "\n";
				break;
			}
			case 'd': {
				tree.erase(key);
				break;
			}
			case 'n': {
				auto next = tree.upper_bound(key);
				cout << (next == tree.end() ? "none" : to_string(*next)) << "\n";
				break;
			}
			case 'p': {
				auto next = tree.lower_bound(key);
				cout << (next != tree.begin() ? to_string(*(--next)) : "none") << "\n";
				break;
			}
		}
	}
	return 0;
}
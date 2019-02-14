//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <unordered_map>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	unordered_map<string, string> map;
	string command, key, value;
	while (cin >> command >> key) {
		const char firstSymbol = command[0];
		if (firstSymbol == 'p') {
			cin >> value;
			if (map.count(key) > 0) {
				map.at(key) = value;
			} else {
				map.emplace(key, value);
			}
		} else if (firstSymbol == 'g') {
			auto iter = map.find(key);
			cout << (iter != map.end() ? iter->second : "none") << "\n";
		} else {
			map.erase(key);
		}
	}
	return 0;
}
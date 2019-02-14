//
// Created by damtev on 30.12.18.
//

#include <iostream>
#include <ext/pb_ds/tree_policy.hpp>
#include <ext/pb_ds/assoc_container.hpp>

using namespace std;
using namespace __gnu_pbds;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	tree<int, null_type, greater<>, rb_tree_tag, tree_order_statistics_node_update> tree;
	int n;
	cin >> n;
	for (int i = 0; i < n; ++i) {
		string command;
		int key;
		cin >> command >> key;
		if (command == "0") {
			--key;
			cout << *tree.find_by_order(key) << "\n";
		} else if (command == "-1") {
			tree.erase(key);
		} else {
			tree.insert(key);
		}
	}
	return 0;
}
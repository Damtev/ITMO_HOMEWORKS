//
// Created by damtev on 30.12.18.
//

#include <iostream>
#include <ext/rope>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	int n, m;
	__gnu_cxx::rope<int> rope;
	cin >> n >> m;
	for (int i = 1; i <= n; ++i) {
		rope.push_back(i);
	}
	for (int j = 0; j < m; ++j) {
		int l, r;
		cin >> l >> r;
		--l;
		--r;
		__gnu_cxx::rope<int> curPart = rope.substr(l, r - l + 1);
		rope.erase(l, r - l + 1);
		rope.insert(rope.mutable_begin(), curPart);
	}
	for (int number : rope) {
		cout << number << " ";
	}
	return 0;
}
//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	int n;
	cin >> n;
	vector<int> vector;
	vector.reserve(n);
	for (int i = 0; i < n; ++i) {
		int cur;
		cin >> cur;
		vector.push_back(cur);
	}
	int m;
	cin >> m;
	for (int j = 0; j < m; ++j) {
		int curRequest;
		cin >> curRequest;
		long first = lower_bound(vector.begin(), vector.end(), curRequest) - vector.begin();
		long last = upper_bound(vector.begin(), vector.end(), curRequest) - vector.begin();
		if (first == last) {
			cout << "-1 -1";
		} else {
			cout << first + 1 << " " << last;
		}
		cout << "\n";
	}
	return 0;
}
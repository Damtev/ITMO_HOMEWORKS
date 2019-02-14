//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <queue>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	queue<int> queue;
	int m;
	cin >> m;
	char command;
	int value;
	for (int i = 0; i < m; ++i) {
		cin >> command;
		if (command == '+') {
			cin >> value;
			queue.push(value);
		} else {
			cout << queue.front() << "\n";
			queue.pop();
		}
	}
	return 0;
}
//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <stack>

using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	stack<int> stack;
	int m;
	cin >> m;
	char command;
	int value;
	for (int i = 0; i < m; ++i) {
		cin >> command;
		if (command == '+') {
			cin >> value;
			stack.push(value);
		} else {
			cout << stack.top() << "\n";
			stack.pop();
		}
	}
	return 0;
}
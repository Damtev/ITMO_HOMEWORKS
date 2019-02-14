//
// Created by damtev on 29.12.18.
//

#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

class Team {
public:
	int id;
	int tasksNumber;
	int penalty;

	Team(int id, int tasksNumber, int penalty) : id(id), tasksNumber(tasksNumber), penalty(penalty) {}

	bool operator<(const Team& other) {
		return (tasksNumber > other.tasksNumber || (tasksNumber == other.tasksNumber && penalty < other.penalty) ||
				(tasksNumber == other.tasksNumber && penalty == other.penalty && id < other.id));
	}
};

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);
	int n;
	cin >> n;
	vector<Team> teams;
	teams.reserve(n);
	for (int i = 1; i <= n; ++i) {
		int tasksNumber, penalty;
		cin >> tasksNumber >> penalty;
		teams.emplace_back(i, tasksNumber, penalty);
	}
	sort(teams.begin(), teams.end());
	for (int j = 0; j < n; ++j) {
		cout << teams[j].id << " ";
	}
	return 0;
}
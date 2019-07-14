#include <vector>
#include <stack>
#include <iostream>
#include <algorithm>
const int N = 50000;
int n;
using namespace std;

long long intersect(pair<int, long long> l1, pair<int, long long> l2) {
	return (l2.second - l1.second) / (l1.first - l2.first);
}
long long eval(pair<int, long long> l, long long x) {
	return l.first * x + l.second;
}
int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(NULL);
	cin >> n;
	pair<int, int> a[n];
	for (int i = 0; i < n; i++) {
		int x, y;
		cin >> x >> y;
		a[i].first = x;
		a[i].second = y;
	}
	sort(a, a + n);
	stack<pair<int, int>> stack;
	vector<pair<int, int>> vec;
	for (auto x : a) {
		while (stack.size() && x.second >= stack.top().second) {
			stack.pop();
		}
		stack.push(x);
	}
	while (stack.size()) {
		vec.push_back(stack.top());
		stack.pop();
	}
	deque<pair<int, long long>> hull;
	long long dp = 0;
	for (int i = vec.size() - 1; i >= 0; i--) {
		pair<int, int> curr = vec[i];
		int x = curr.first, y = curr.second;
		pair<int, long long> line = { y, dp };

		while (hull.size() >= 2 && intersect(hull[hull.size() - 2], line) >= intersect(hull[hull.size() - 1], line)) {
			hull.pop_back();
		}
		hull.push_back(line);
		while (hull.size() >= 2 && eval(hull[0], x) >= eval(hull[1], x)) {
			hull.pop_front();
		}
		dp = eval(hull.front(), x);
	}
	cout << dp << "\n";

}

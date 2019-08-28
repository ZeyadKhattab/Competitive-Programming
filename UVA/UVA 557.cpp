#include <bits/stdc++.h>
using namespace std;

int main() {
	ios_base::sync_with_stdio(0);
	cin.tie(0);
	cout.tie(0);
	int tc, n;
	cin >> tc;
	while (tc--) {
		cin >> n;

		double ans = 1;
		int m = n / 2 - 1;
		int div = n - 2;
		for (int i = 1; i <= m; i++) {
			ans = ans * (m + i) / i;
			while (ans >= 1 && div-- > 0) {
				ans /= 2;
			}
		}
		while (div-- > 0)
			ans /= 2;
		printf("%.4f\n", 1 - ans);

	}

}


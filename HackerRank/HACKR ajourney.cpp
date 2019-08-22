#include <bits/stdc++.h>
using namespace std;

#define log2  0.30102999566398119521373889472449L
int pow(long b, int e, int mod) {
	b %= mod;
	long ans = 1;
	while (e > 0) {
		if (e % 2 == 1)
			ans = ans * b % mod;
		e >>= 1;
		b = b * b % mod;
	}

	return (int) ans;
}

int main() {

	int tc, n, k;
	cin >> tc;
	while (tc-- > 0) {
		cin >> n >> k;
		n--;

		int mod = pow(10, k);
		int last = pow(2, n, mod);
		int digits = (long long int) (log2 * n + 1);
		int x = digits - k;
		long double ans = (n * log2 - x);
		long long int first = (long long int) pow(10, ans);

		cout << first + last << "\n";
	}

}


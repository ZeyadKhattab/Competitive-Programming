
public class Maths {
	static int MOD = (int) 1e9 + 7;

	static int add(int a, int b) {
		a += b;
		if (a >= MOD)
			a -= MOD;
		if (a < 0)
			a += MOD;
		return a;
	}

	static int multiply(int a, int b) {
		return (int) (a * 1L * b % MOD);
	}

	static int pow(int b, long e) {
		int ans = 1;
		while (e > 0) {
			if ((e & 1) == 1)
				ans = multiply(ans, b);
			e >>= 1;
			b = multiply(b, b);
		}
		return ans;
	}

	static int multiplyExp(long a, long b) {
		if (a == 0)
			return 0;
		if ((b & 1) == 0)
			return multiply(2, multiplyExp(a >> 1, b));
		return add((int) b % MOD, multiply(2, multiplyExp(a - 1 >> 1, b)));
	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static long lcm(int a, int b) {
		return a / gcd(a, b) * 1L * b;
	}
}

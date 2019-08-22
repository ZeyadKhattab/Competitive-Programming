public class StrongEconomy {

	static long ceil(long x, long y) {
		return (x + y - 1) / y;
	}

	static long solve(long gold, long target, long price, long n, long k, long buy) {

		gold -= buy * price;
		long toEqual = Math.min(Math.abs(n - k), buy);
		if (n < k)
			n += toEqual;
		else
			k += toEqual;
		buy -= toEqual;
		n += buy / 2;
		k += (buy + 1) / 2;
		if (k >= ceil(target - gold, n))
			return 1;
		return ceil(target - gold, n * k);

	}

	public static long earn(long n, long k, long price, long target) {
		long ans = Long.MAX_VALUE;
		long gold = 0;
		long total = 0;
		while (true) {

			long x = solve(gold, target, price, n, k, 0);
			ans = Math.min(ans, total + x);
			if (x == 1)
				break;
			long max = ceil(price - gold, n * k);
			gold += max * n * k;
			total += max;
			long maxToBuy = gold / price;
			for (long buy = 1; buy <= maxToBuy; buy++) {
				x = solve(gold, target, price, n, k, buy);
				ans = Math.min(ans, total + x);
				if (x == 1) {
					return ans;
				}
			}
			gold -= maxToBuy * price;
			long toEqual = Math.min(Math.abs(n - k), maxToBuy);
			if (n < k)
				n += toEqual;
			else
				k += toEqual;
			maxToBuy -= toEqual;
			n += maxToBuy / 2;
			k += (maxToBuy + 1) / 2;

		}
		return ans;
	}

}

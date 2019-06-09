/*
 * This problem can be solved by 2 separate dps where one calculates the max product and the other calculates the min
 * depending on the sign of the product so far
 */
import java.util.Arrays;

public class TheProduct {
	static long INF = (long) 1e18;
	static int n, maxDist, k, a[];
	static long[][][] memo1, memo2;

	static long dpMax(int idx, int d, int left) { // pos
		if (d > maxDist && left == k)
			d = maxDist;
		if (left == 0)
			return 1;
		if (memo1[idx][d][left] != INF)
			return memo1[idx][d][left];
		long ans = a[idx] * (a[idx] >= 0 ? dpMax(idx + 1, 1, left - 1) : dpMin(idx + 1, 1, left - 1));
		if ((d < maxDist || left == k) && n - idx > left)
			ans = Math.max(ans, dpMax(idx + 1, d + 1, left));
		return memo1[idx][d][left] = ans;
	}

	static long dpMin(int idx, int d, int left) { // neg
		if (d > maxDist && left == k)
			d = maxDist;
		if (left == 0)
			return 1;
		if (memo2[idx][d][left] != INF)
			return memo2[idx][d][left];
		long ans = a[idx] * (a[idx] <= 0 ? dpMax(idx + 1, 1, left - 1) : dpMin(idx + 1, 1, left - 1));
		if ((d < maxDist || left == k) && n - idx > left)
			ans = Math.min(ans, dpMin(idx + 1, d + 1, left));
		return memo2[idx][d][left] = ans;

	}

	public static long maxProduct(int[] numbers, int K, int maxD) {
		n = numbers.length;
		maxDist = maxD;
		a = numbers;
		k = K;
		memo1 = new long[n][maxDist + 1][k + 1];
		memo2 = new long[n][maxDist + 1][k + 1];
		for (int i = 0; i < n; i++)
			for (int j = 0; j <= maxDist; j++) {
				Arrays.fill(memo1[i][j], INF);
				Arrays.fill(memo2[i][j], INF);

			}
		return dpMax(0, 0, k);
	}

}

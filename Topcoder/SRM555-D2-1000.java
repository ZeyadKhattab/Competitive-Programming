
// let dp(len,rem,hole) be number of configurations of length len such that
// it has to have rem muddy holes and hole is a flag which represents whether the first segment can be a hole or not
// for each sequence, we will iterate over the next 2 positions and consider each case independantly
import java.util.Arrays;

public class MuddyRoad2 {
	static int[][] nck;
	static int MOD = 555555555;
	static int[][][] memo;

	static int dp(int len, int rem, int p) {

		if (rem < 0)
			return 0;
		if (len == 0)
			return 0;
		if (len == 1)
			return rem == 1 && p == 1 ? 1 : 0;
		if (len == 2) {
			int ans = 0;
			// 00
			if (rem == 0)
				ans++;
			// 01=> odd
			// 10
			if (rem == 1 && p == 1)
				ans++;
			// 11
			if (p == 1 && rem == 2)
				ans++;

			return ans;
		}
		if (memo[p][rem][len] != -1)
			return memo[p][rem][len];
		long ans = 0;
		// len>2
		// 00
		ans += dp(len - 3, rem, 1);
		// 01
		ans += dp(len - 3, rem - 1, 1);
		// 10
		ans += dp(len - 2, rem - 1, 0);
		// 11
		if (rem >= 2 && len >= 3)
			ans += nck[len - 3][rem - 2];
		// put a muddy segment
		if (p == 1 && rem >= 1) {
			ans += nck[len - 1][rem - 1];
		}
		ans %= MOD;
		return memo[p][rem][len] = (int) ans;

	}

	public int theCount(int N, int muddyCount) {
		nck = new int[N + 1][N + 1];
		for (int i = 0; i < nck.length; i++) {
			nck[i][0] = 1;
			for (int j = 1; j <= i; j++)
				nck[i][j] = (int) ((nck[i - 1][j - 1] + 0L + nck[i - 1][j]) % MOD);
		}
		memo = new int[2][muddyCount + 1][N];
		for (int[][] x2 : memo)
			for (int[] x : x2)
				Arrays.fill(x, -1);
		return dp(N - 1, muddyCount, 0);
	}

}

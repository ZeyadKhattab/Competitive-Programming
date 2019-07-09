public class BagsOfGold {
	static int n, memo[][];
	static int[] a;

	public int netGain(int[] bags) {
		a = bags;
		n = a.length;
		memo = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				memo[i][j] = -1;
		return dp(0, n - 1);
	}

	static int dp(int l, int r) {
		if (l > r)
			return 0;
		if (memo[l][r] != -1)
			return memo[l][r];
		int ans = Math.max(a[l] - dp(l + 1, r), a[r] - dp(l, r - 1));
		return memo[l][r] = ans;

	}

}

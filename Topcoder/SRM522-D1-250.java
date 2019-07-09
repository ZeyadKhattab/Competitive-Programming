public class RowAndCoins {
	static int n, memo[][];
	static char[] s;

	public String getWinner(String cells) {
		s = cells.toCharArray();
		n = s.length;
		memo = new int[2][1 << n];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 1 << n; j++)
				memo[i][j] = -1;
		int winner = dp(0, 0);
		return winner == 0 ? "Alice" : "Bob";
	}

	static int dp(int msk, int turn) {
		if (memo[turn][msk] != -1)
			return memo[turn][msk];
		int zeroes = 0, idx = -1;
		for (int i = 0; i < n; i++)
			if ((msk & 1 << i) == 0) {
				zeroes++;
				idx = i;
			}
		int ans = 1 ^ turn;
		if (zeroes == 1)
			ans = s[idx] - 'A';
		else {
			for (int l = 0; l < n; l++) {
				if ((msk & 1 << l) > 0)
					continue;
				int newMsk = msk;
				for (int r = l; r < n; r++) {
					if ((msk & 1 << r) > 0 || r - l + 1 == zeroes)
						break;
					newMsk |= 1 << r;
					int winner = dp(newMsk, turn ^ 1);
					if (winner == turn)
						ans = turn;
				}
			}

		}
		return memo[turn][msk] = ans;

	}

}

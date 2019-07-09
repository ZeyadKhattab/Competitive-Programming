import java.util.*;

public class EllysCheckers {
	static int n, memo[];

	public String getWinner(String board) {
		int msk = 0;
		n = board.length();
		memo = new int[1 << n];
		for (int i = 0; i < n; i++) {
			if (board.charAt(i) == 'o')
				msk |= 1 << i;
		}
		Arrays.fill(memo, -1);
		return dp(msk) == 1 ? "YES" : "NO";
	}

	static int dp(int msk) {
		if (memo[msk] != -1)
			return memo[msk];
		int ans = 0;
		for (int i = 0; i + 1 < n; i++) {
			int bit1 = msk & (1 << i);
			if (bit1 == 0)
				continue;
			int bit2 = i + 1 == n - 1 ? 0 : msk & (1 << (i + 1));
			int newMsk = msk - bit1;
			if (bit2 == 0) {
				int x = dp(newMsk | (1 << (i + 1)));
				if (x == 0)
					ans = 1;
			}
			if (i + 3 < n) {
				bit2 = i + 3 == n - 1 ? 0 : msk & (1 << (i + 3));
				if (bit2 == 0) {
					int x = dp(newMsk | (1 << (i + 3)));
					if (x == 0)
						ans = 1;
				}
			}
		}
		return memo[msk] = ans;
	}
}

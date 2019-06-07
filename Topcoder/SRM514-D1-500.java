
/*
 * First, we can see that cell (i,j) and cells (i+k*n,j+b*m) for any constants k & b must have the same parity 
 * the 2 sequences i,i+1,i+2,...,i+n-1 and i+1,i+2,..,i+n-1, i+n only have i replaced by i+n, so for the 2 sequences to have
 * the same parity of sum, i and i+n must have the same parity, so now we can solve the problem for an n*m grid
 * and we will solve it using dp(row,msk) where msk represents the cumulative parity of all the columns
 */
import java.util.Arrays;

public class MagicalGirlLevelTwoDivOne {

	static int even = 0, odd = 1, unkown = -1, MOD = (int) 1e9 + 7;
	static int[][] memo, pow, cnt, grid;

	public static int theCount(String[] s, int n, int m) {
		int r = s.length, c = s[0].length();
		grid = new int[r][c];
		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				grid[i][j] = s[i].charAt(j) - '0';
		cnt = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {

				boolean foundOdd = false, foundEven = false;
				for (int i2 = i; i2 < r; i2 += n)
					for (int j2 = j; j2 < c; j2 += m) {
						int x = grid[i2][j2];
						if (x == -2) {
							cnt[i][j]++;
							continue;
						}
						if (x % 2 == 0)
							foundEven = true;
						else
							foundOdd = true;
					}
				if (foundEven && foundOdd)
					return 0;
				if (!foundEven && !foundOdd)
					grid[i][j] = unkown;
				else
					grid[i][j] = foundEven ? even : odd;
			}
		pow = new int[2][r * c + 1];
		for (int b = 4; b <= 5; b++) {
			pow[b - 4][0] = 1;
			long ans = 1;
			for (int i = 1; i < pow[0].length; i++) {
				ans = ans * b % MOD;
				pow[b - 4][i] = (int) ans;
			}
		}
		memo = new int[n][1 << m];
		for (int i = 0; i < n; i++)
			Arrays.fill(memo[i], -1);
		return solve(0, 0, n, m);
	}

	static int solve(int row, int msk, int r, int c) {

		if (row == r)
			return msk + 1 == 1 << c ? 1 : 0;
		if (memo[row][msk] != -1)
			return memo[row][msk];
		int ans = 0;
		out: for (int newMsk = 0; newMsk < 1 << c; newMsk++) {
			long curr = 1;
			for (int bit = 0; bit < c; bit++) {
				int currBit = (newMsk & 1 << bit) == 0 ? 0 : 1;
				if (grid[row][bit] != currBit && grid[row][bit] != unkown)
					continue out;

				curr *= pow[currBit][cnt[row][bit]];
				curr %= MOD;

			}
			if (Integer.bitCount(newMsk) % 2 == 1) {
				ans += curr * 1L * solve(row + 1, msk ^ newMsk, r, c) % MOD;
				if (ans >= MOD)
					ans -= MOD;
			}
		}

		return memo[row][msk] = ans;
	}

}

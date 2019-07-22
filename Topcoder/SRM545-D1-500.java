/*
 * Let's try all starting positions with all possible vectors going to the first point,
 * then for each path we will count the number of grid cells, let it be X and then add X choose K 
 * to the answer, this solution has complexity L*L*H, to optimize, we can remove the last loop
 * which tries all possible y component of vector and we will split the addition in 2 parts,
 * one where the number of cells visited in the x directions is minimum and similarly for the y direction
 * and we will pre-process these numbers to speed the execution
 */
import java.util.*;

public class Spacetsk {

	static int[][] compute(int n, int mod) {
		int[][] nck = new int[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			nck[i][0] = 1;
			for (int j = 1; j <= i; j++) {
				nck[i][j] = nck[i - 1][j - 1] + nck[i - 1][j];
				if (nck[i][j] >= mod)
					nck[i][j] -= mod;
			}
		}
		return nck;
	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static int[][] pre1(int X, int Y) {
		int[][] ans = new int[X + 1][Y + 1];
//		ans[x][y] = the number of numbers <=y that are coprime to x
		for (int x = 0; x <= X; x++)
			for (int y = 1; y <= Y; y++) {
				ans[x][y] = ans[x][y - 1];
				if (gcd(x, y) == 1)
					ans[x][y]++;
			}

		return ans;
	}

	static int[][] pre2(int X, int Y, int[][] nck, int k) {
		int[][] ans = new int[X + 1][Y + 2];
		for (int x = 0; x <= X; x++)
			for (int y = Y; y > 0; y--) {
				ans[x][y] = ans[x][y + 1];
				if (gcd(x, y) != 1)
					continue;
				int op = steps(y, Y, 0);
				int val = op >= k ? nck[op][k] : 0;
				ans[x][y] += val;
				if (ans[x][y] >= MOD)
					ans[x][y] -= MOD;

			}

		return ans;
	}

	static int INF = (int) 1e9;;

	static int steps(int a, int n, int pos) {
		if (a == 0)
			return INF;
		if (a > 0) {
			// pos+k*a<=n
			return (n - pos) / a + 1;
		} else {
			a *= -1;
			// pos-k*a>=0
			return pos / a + 1;
		}
	}

	static int MOD = (int) 1e9 + 7;

	static int getMid(int x, int H) {
		int lo = 1, hi = H;
		int ans = H + 1;
		while (lo <= hi) {
			int mid = lo + hi >> 1;
			if (H / mid + 1 <= x) {
				ans = mid;
				hi = mid - 1;
			} else
				lo = mid + 1;
		}
		return ans;
	}

	public static int countsets(int L, int H, int K) {
		long ans = 0;
		if (K == 1) {
			return (L + 1) * (H + 1);
		}
		int[][] nck = compute(Math.max(L, H) + 1, MOD);
		int[][] pre1 = pre1(L, H), pre2 = pre2(L, H, nck, K);
		for (int startX = 0; startX <= L; startX++) {
			for (int x = -startX; x + startX <= L; x++) {
				int stepsX = steps(x, L, startX);
				int midPoint = getMid(stepsX, H);

				// from 1 to midPoint - 1
				if (midPoint != 1)
					ans += (pre1[Math.abs(x)][midPoint - 1]) * 1L * (K <= stepsX ? nck[stepsX][K] : 0) % MOD;
				// from midPoint to H
				ans += pre2[Math.abs(x)][midPoint];
				ans %= MOD;
			}
		}
		return (int) ans;
	}
}

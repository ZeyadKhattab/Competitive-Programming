import java.io.*;
import java.util.*;

public class PlaylistForPolycarp  {

	static int n, t[], g[], MOD = (int) 1e9 + 7;
	static int[][][] memo1, memo2[], memo3[];

	// subset sum for songs of genre 0
	static int dp1(int idx, int remCnt, int remSum) {
		if (idx == n)
			return remSum == 0 && remCnt == 0 ? 1 : 0;
		if (remCnt < 0 || remSum < 0)
			return 0;
		if (memo1[idx][remCnt][remSum] != -1)
			return memo1[idx][remCnt][remSum];
		int ans = dp1(idx + 1, remCnt, remSum);
		if (g[idx] == 0) {
			ans += dp1(idx + 1, remCnt - 1, remSum - t[idx]);
			if (ans >= MOD)
				ans -= MOD;
		}
		return memo1[idx][remCnt][remSum] = ans;
	}

	// subset sum for songs of genre 1 && 2
	static int dp2(int idx, int remCnt1, int remCnt2, int remSum) {

		if (idx == n)
			return remSum == 0 && remCnt1 == 0 && remCnt2 == 0 ? 1 : 0;
		if (remSum < 0 || remCnt1 < 0 || remCnt2 < 0)
			return 0;
		if (memo2[idx][remCnt1][remCnt2][remSum] != -1)
			return memo2[idx][remCnt1][remCnt2][remSum];
		int ans = dp2(idx + 1, remCnt1, remCnt2, remSum);

		if (g[idx] == 1)
			ans += dp2(idx + 1, remCnt1 - 1, remCnt2, remSum - t[idx]);
		else if (g[idx] == 2)
			ans += dp2(idx + 1, remCnt1, remCnt2 - 1, remSum - t[idx]);
		if (ans >= MOD)
			ans -= MOD;
		return memo2[idx][remCnt1][remCnt2][remSum] = ans;
	}

	// how many ways to arrange cnt0 zeroes cnt1 ones and cnt2 twos such that no 2
	// consecutive genres are equal
	private static int dp3(int cnt0, int cnt1, int cnt2, int last) {
		if (cnt0 + cnt1 + cnt2 == 0)
			return 1;
		if (memo3[last][cnt0][cnt1][cnt2] != -1)
			return memo3[last][cnt0][cnt1][cnt2];
		long ans = 0;
		if (cnt0 > 0 && last != 0)
			ans += dp3(cnt0 - 1, cnt1, cnt2, 0);
		if (cnt1 > 0 && last != 1)
			ans += dp3(cnt0, cnt1 - 1, cnt2, 1);
		if (cnt2 > 0 && last != 2)
			ans += dp3(cnt0, cnt1, cnt2 - 1, 2);
		return memo3[last][cnt0][cnt1][cnt2] = (int) (ans % MOD);

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		n = sc.nextInt();
		int[] fac = new int[n + 1];
		t = new int[n];
		g = new int[n];
		int[] cnt = new int[3];
		fac[0] = 1;
		for (int i = 1; i <= n; i++)
			fac[i] = (int) (i * 1L * fac[i - 1] % MOD);
		int T = sc.nextInt();
		for (int i = 0; i < n; i++) {
			t[i] = sc.nextInt();
			g[i] = sc.nextInt() - 1;
			cnt[g[i]]++;

		}
		memo1 = new int[n][cnt[0] + 1][T + 1];
		memo2 = new int[n][cnt[1] + 1][cnt[2] + 1][T + 1];
		memo3 = new int[4][cnt[0] + 1][cnt[1] + 1][cnt[2] + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= cnt[0]; j++)
				Arrays.fill(memo1[i][j], -1);
			for (int j = 0; j <= cnt[1]; j++)
				for (int k = 0; k <= cnt[2]; k++)
					Arrays.fill(memo2[i][j][k], -1);

		}
		for (int i = 0; i < 4; i++)
			for (int j = 0; j <= cnt[0]; j++)
				for (int k = 0; k <= cnt[1]; k++)
					Arrays.fill(memo3[i][j][k], -1);
		int ans = 0;
		for (int cnt0 = 0; cnt0 <= cnt[0]; cnt0++)
			for (int sum0 = 0; sum0 <= T; sum0++)
				for (int cnt1 = 0; cnt1 <= cnt[1]; cnt1++)
					for (int cnt2 = 0; cnt2 <= cnt[2]; cnt2++) {
						long ways = dp1(0, cnt0, sum0) * 1L * dp2(0, cnt1, cnt2, T - sum0) % MOD;
						ways = ways * dp3(cnt0, cnt1, cnt2, 3) % MOD;
						ways *= fac[cnt0];
						ways %= MOD;
						ways *= fac[cnt1];
						ways %= MOD;
						ways *= fac[cnt2];
						ways %= MOD;
						ans += ways;
						if (ans >= MOD)
							ans -= MOD;
					}
		out.println(ans);
		out.close();

	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		Scanner(String fileName) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(fileName));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}

	}

}

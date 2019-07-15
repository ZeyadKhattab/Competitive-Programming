import java.io.*;
import java.util.*;

public class B {

	static long[][] cost;
	static int n, x[], w[];
	static long INF = (long) 1e18;
	static long[][] dp;

	static void compute(int groups, int L, int R, int optL, int optR) {
		if (L > R)
			return;
		int m = L + R >> 1;
		long ans = Long.MAX_VALUE;
		int curropT = -1;
		for (int k = optL; k <= Math.min(m, optR); k++) {
			if (cost[m][k] + dp[groups - 1][k - 1] < ans) {
				ans = cost[m][k] + dp[groups - 1][k - 1];
				curropT = k;
			}
		}
		dp[groups][m] = ans;
		compute(groups, L, m - 1, optL, curropT);
		compute(groups, m + 1, R, curropT, optR);
	}

	static void preProcess() {
		cost = new long[n + 1][n + 1];
		for (int l = 1; l <= n; l++) {
			cost[l][l] = 0;
			long sumLeft = w[l], sumRight = 0, currCost = 0;
			int best = l;
			for (int r = l + 1; r <= n; r++) {
				sumRight += w[r];
				int d = x[r] - x[best];
				currCost += w[r] * 1L * d;
				d = x[best + 1] - x[best];
				while (best < r && currCost > currCost + sumLeft * d - sumRight * d) {
					currCost += sumLeft * d - sumRight * d;
					sumLeft += w[++best];
					sumRight -= w[best];
					if (best < r)
						d = x[best + 1] - x[best];

				}
				cost[r][l] = currCost;

			}
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		n = sc.nextInt();
		int K = sc.nextInt();
		w = new int[n + 1];
		x = new int[n + 1];
		dp = new long[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			x[i] = sc.nextInt();
			w[i] = sc.nextInt();
			Arrays.fill(dp[i], INF);
		}
		for (int i = 1; i <= n; i++)
			dp[0][i] = INF;
		preProcess();

		for (int groups = 1; groups <= K; groups++) {
			compute(groups, 1, n, 1, n);
		}

		out.println(dp[K][n]);
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

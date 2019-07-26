
/*
 * dp(i,j) is the min cost for i piles and prefix ending at j
 * for each j, we want to know the optimal place it will end in and add 
 *  cost of moving all elements from optimal to j and the prefix ending before optimal 
 *  dp(i,j) = dp(i-1,optimal-1) + cost (optimal,j)
 *  we can notice that for a fixed number piles the optimal is increasing,
 *  therefore, we can apply DP D&C
 */
import java.io.*;
import java.util.*;

public class NKLEAVES {

	static long INF = (long) 1e18;
	static int n, K;
	static long[] cost, prefixSum, dp[];

	static long getCost(int l, int r) {
		long ans = cost[r] - cost[l - 1];
		long sum = prefixSum[r] - prefixSum[l - 1];
		return ans - l * sum;
	}

	static void preProcess(int[] a) {
		cost = new long[n + 1];
		prefixSum = new long[n + 1];
		for (int i = 1; i <= n; i++) {
			cost[i] = i * 1L * a[i] + cost[i - 1];
			prefixSum[i] = prefixSum[i - 1] + a[i];
		}
	}

	static void compute(int piles, int l, int r, int optL, int optR) {
		if (l > r)
			return;
		int mid = l + r >> 1;
		int opt = 0;
		long ans = INF;
		for (int i = optL; i <= Math.min(mid, optR); i++) {
			long x = dp[piles - 1][i - 1] + getCost(i, mid);
			if (x < ans) {
				ans = x;
				opt = i;
			}
		}
		dp[piles][mid] = ans;
		compute(piles, l, mid - 1, optL, opt);
		compute(piles, mid + 1, r, opt, optR);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		n = sc.nextInt();
		K = sc.nextInt();
		int[] a = new int[n + 1];
		dp = new long[K + 1][n + 1]; // dp[i][j] = min cost for i piles for prefix ending at j
		for (long[] x : dp)
			Arrays.fill(x, INF);

		for (int i = 1; i <= n; i++)
			a[i] = sc.nextInt();
		preProcess(a);

		for (int piles = 1; piles <= K; piles++) {
			dp[piles - 1][0] = 0;
			compute(piles, 1, n, 1, n);
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

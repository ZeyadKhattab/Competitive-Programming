
/*
 * dp(k,i) = max sum for partitioning the prefix ending at i into k subarrays
 * Because opt(k,i-1)<=opt(k,i)<=opt(k+1,i),
 * one can use knuth optimization
 */
import java.io.*;
import java.util.*;

public class CHEFAOR {

	static int[][] cost;
	static long[][] dp;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), K = sc.nextInt(), a[] = new int[n + 1], opt[][] = new int[n + 1][n + 1];
			for (int i = 1; i <= n; i++)
				a[i] = sc.nextInt();
			dp = new long[n + 1][n + 1];
			cost = new int[n + 1][n + 1];
			for (int i = 1; i <= n; i++) {
				int ans = 0;
				for (int j = i; j <= n; j++) {
					ans |= a[j];
					cost[i][j] = ans;
				}
				dp[i][i] = a[i] + dp[i - 1][i - 1];
				opt[i][i] = i - 1;
			}
			for (int diff = 1; diff < n; diff++)
				for (int k = 1; k + diff <= n; k++) {
					int i = k + diff;
					int L = opt[k][i - 1], R = k == n ? n - 1 : opt[k + 1][i];
					for (int j = L; j <= R; j++) {
						long x = dp[k - 1][j] + cost[j + 1][i];
						if (x > dp[k][i]) {
							dp[k][i] = x;
							opt[k][i] = j;
						}

					}
				}
			out.println(dp[K][n]);

		}
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

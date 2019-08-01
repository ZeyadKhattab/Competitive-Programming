// dp using knuth optimization
import java.io.*;
import java.util.*;

public class OptimalBinarySearchTree {

	static int[] sum, dp[];
	static int INF = (int) 1e9;

	static int cost(int l, int r) {
		return sum[r] - sum[l - 1];
	}


	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			sum = new int[n + 1];
			dp = new int[n + 2][n + 1];
			int[][] opt = new int[n + 1][n + 1];
			for (int i = 1; i <= n; i++) {
				sum[i] = sum[i - 1] + sc.nextInt();
				opt[i][i] = i;
			}
			for (int len = 2; len <= n; len++) {
				for (int l = 1; l + len <= n + 1; l++) {
					int r = l + len - 1;
					int ans = INF;
					int L = opt[l][r - 1], R = opt[l + 1][r];
					for (int root = L; root <= R; root++) {
						int x = dp[l][root - 1] + dp[root + 1][r] + cost(l, root - 1) + cost(root + 1, r);
						if (x < ans) {
							ans = x;
							opt[l][r] = root;
						}
					}
					dp[l][r] = ans;
				}
			}
			out.println(dp[1][n]);
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

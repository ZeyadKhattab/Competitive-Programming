// solution: http://lbv-pc.blogspot.com/2012/06/winning-streak.html
import java.io.*;
import java.util.*;

public class WinningStreak {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);

		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			double p = sc.nextDouble();
			double[][] dp = new double[n + 1][n + 1];
			// dp[games][streak]
			for (int j = 0; j <= n; j++)
				dp[0][j] = 1;
			for (int i = 1; i <= n; i++)
				for (int j = 0; j <= n; j++) {
					dp[i][j] = dp[i - 1][j];
					if (j == i - 1)
						dp[i][j] = 1 - Math.pow(p, i);
					else if (j < i - 1) {
						// (i-j-2) games, 1L, j+1 wins
						dp[i][j] -= Math.pow(p, j + 1) * (1 - p) * dp[i - j - 2][j];
					}

				}
			double ans = 0;
			for (int i = 1; i <= n; i++)
				ans += i * (dp[n][i] - dp[n][i - 1]);
			out.println(ans);
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

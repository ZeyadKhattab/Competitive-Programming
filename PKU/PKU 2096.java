
/*
 *
 * dp[i][j]= expected value of days given that i types of bugs have been discovered in j subsystems
 * dp[i][j] = p1 *dp[i][j] + p2*dp[i+1][j] + p3 * dp[i][j+1] + p4*dp[i+1][j+1] + 1
 *  dp[i][j] (1-p1) = p2*dp[i+1][j] + p3*dp[i][j+1] + p4*dp[i+1][j+1] +1
 * dp[i][j] = (p2*dp[i+1][j] + p3*dp[i][j+1] + p4*dp[i+1][j+1] +1) / (1-p1)
 
 */

import java.io.*;
import java.util.*;

public class CollectingBugs {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), s = sc.nextInt(), ns = n * s;
		double[][] dp = new double[n + 2][s + 2];

		dp[n][s] = 0;
		for (int i = n; i >= 0; i--)
			for (int j = s; j >= 0; j--) {
				if (i == n && j == s)
					continue;
				double p1 = i * j * 1.0 / ns, p2 = (n - i) * j * 1.0 / ns, p3 = i * (s - j) * 1.0 / ns,
						p4 = (n - i) * (s - j) * 1.0 / ns;
				dp[i][j] = (p2 * dp[i + 1][j] + p3 * dp[i][j + 1] + p4 * dp[i + 1][j + 1] + 1) / (1 - p1);

			}

		out.printf("%.4f\n", dp[0][0]);
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

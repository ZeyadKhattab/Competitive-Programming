
/*
 * Let dp[k][u][v] be best profit from moving to currency u to currency v
 * using exactly k steps,
 * the transitions of this dp is similar to floyd warshal algorithm
 */
import java.io.*;
import java.util.*;

public class Arbitrage {

	static double EPS = 1e-9;
	static PrintWriter out = new PrintWriter(System.out);
	static int[][][] par;

	static void print(int curr, int left, int start, boolean end) {
		if (left > 0)
			print(par[left][start][curr], left - 1, start, false);
		out.print(curr + 1);
		if (end)
			out.println();
		else
			out.print(" ");
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner();
		while (sc.ready()) {
			int n = sc.nextInt();
			double[][] a = new double[n][n], dp[] = new double[n + 1][n][n];
			par = new int[n + 1][n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					if (i == j) {
						a[i][j] = 1;
						dp[0][i][j] = 1;
					} else {
						a[i][j] = sc.nextDouble();
						dp[1][i][j] = a[i][j];
						par[1][i][j] = i;
					}

				}
			for (int k = 2; k <= n; k++)
				for (int u = 0; u < n; u++)
					for (int v = 0; v < n; v++) {
						for (int connect = 0; connect < n; connect++) {
							double cand = dp[k - 1][u][connect] * dp[1][connect][v];
							if (cand > dp[k][u][v]) {

								dp[k][u][v] = cand;
								par[k][u][v] = connect;
							}
						}
					}
			boolean found = false;
			for (int k = 2; k <= n && !found; k++) {
				for (int i = 0; i < n && !found; i++) {
					if (dp[k][i][i] > 1.01) {
						print(i, k, i, true);
						found = true;
					}
				}
			}
			if (!found) {
				out.println("no arbitrage sequence exists");
			}
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

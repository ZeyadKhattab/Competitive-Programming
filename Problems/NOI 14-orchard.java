
/*
 * for any rectangle ans= zeroes in rectangles + ones outside
 * = total ones-ones in rectangle +  zeroes inside
 * which means that we are looking for the maximum value ones-zeroes
 * so, let's replace each zero by -1 and find the rectangle with the maximum sum
 */
import java.io.*;
import java.util.*;

public class orchard {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt();
		int[][] a = new int[n][m], prefix = new int[n][m];
		int ones = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				a[i][j] = sc.nextInt();
				if (a[i][j] == 0)
					a[i][j] = -1;
				else
					ones++;
				prefix[i][j] = a[i][j];
				if (i > 0)
					prefix[i][j] += prefix[i - 1][j];
				if (j > 0)
					prefix[i][j] += prefix[i][j - 1];
				if (i > 0 && j > 0)
					prefix[i][j] -= prefix[i - 1][j - 1];
			}

		int ans = 0;
		for (int u = 0; u < n; u++) {

			for (int d = u; d < n; d++) {
				int min = 0;
				for (int j = 0; j < m; j++) {
					int sum = prefix[d][j];
					if (u > 0) {
						sum -= prefix[u - 1][j];
					}

					ans = Math.max(sum - min, ans);
					min = Math.min(min, sum);
				}
			}
		}
		out.println(ones - ans);
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

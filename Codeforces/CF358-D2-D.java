import java.io.*;
import java.util.*;

public class DimaAndHares {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[][] a = new int[n][3];
		for (int j = 0; j < 3; j++)
			for (int i = 0; i < n; i++)
				a[i][j] = sc.nextInt();
		int[][] dp = new int[n + 1][2];
		for (int idx = n - 1; idx >= 0; idx--)
			for (int before = 0; before <= 1; before++)
				for (int nxt = 0; nxt <= 1; nxt++)
					dp[idx][before] = Math.max(
							dp[idx + 1][nxt] + a[idx][(idx == 0 ? 0 : before) + (idx == n - 1 ? 0 : (1 ^ nxt))],
							dp[idx][before]);
		out.println(dp[0][0]);
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

	}

}

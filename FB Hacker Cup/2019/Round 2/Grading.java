import java.io.*;
import java.util.*;

public class Grading {
	static char[][] a;
	static int n, m, dp[];
	static int[][][] memo;

	// min papers to lose to achieve at most switches context switches
	static int dp(int last, int switches, int col, int idx) {
		if (idx == n + 1)
			return 0;
		if (memo[last][idx][switches] != -1)
			return memo[last][idx][switches];
		// lose this paper
		int ans = idx == 0 ? n * m : dp(last, switches, col, idx + 1) + 1;
		// do it normally
		if (switches > 0 || a[idx][col] - 'A' == last)
			ans = Math.min(ans,
					dp(a[idx][col] - 'A', a[idx][col] - 'A' == last ? switches : switches - 1, col, idx + 1));
		return memo[last][idx][switches] = ans;
	}

	static void solve(char c) {
		Arrays.fill(a[0], c);
		int[] x = new int[n + 2];

		for (int col = 0; col < m; col++) {

			for (int[][] a2 : memo)
				for (int[] a1 : a2)
					Arrays.fill(a1, -1);
			for (int switches = 0; switches <= n + 1; switches++)
				x[switches] += dp(2, switches, col, 0);
		}
		for (int i = 0; i <= n + 1; i++)
			if (c == 'A')
				dp[i] = x[i];
			else
				dp[i] = Math.min(dp[i], x[i]);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner("grading.txt");
		PrintWriter out = new PrintWriter("output.txt");
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			n = sc.nextInt();
			m = sc.nextInt();
			int K = sc.nextInt();
			a = new char[n + 1][m];
			for (int i = 1; i <= n; i++)
				a[i] = sc.next().toCharArray();
			dp = new int[n+2];
			memo = new int[3][n + 2][n + 2];
			solve('A');
			solve('B');
			out.printf("Case #%d:", t);
			int[] ans = new int[n * m];
			int currSwitches = n + 1;
			for (int lose = 0; lose < n * m; lose++) {
				while (currSwitches >= 0 && lose >= dp[currSwitches])
					currSwitches--;
				ans[lose] = currSwitches + 1;
			}
			while (K-- > 0) {
				out.print(" " + ans[sc.nextInt()]);
			}
			out.println();
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

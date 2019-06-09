
/*
 * We will try all different number of games (t), and for each t, there is a unique s or no suitable s,
 * we will find this s by simulating the match by jumping t games at a time using binary search and at the end verifying that one player
 * one more sets than the other , number of games + (n/1 +n/2 +n/3+n/4+....n/n)*log(n) which is equal n+n(log(n))*log(n) so 
 * overall complexity is n*log^2(n)
 */
import java.io.*;
import java.util.*;

public class TennisGame {

	static int[][] prefix;

	static int check(int games) {
		int[] sets = new int[2], won = new int[2];
		int n = prefix[0].length;
		while (true) {
			int lo = 0, hi = n - 1, nxt = -1;
			while (lo <= hi) {
				int mid = lo + hi >> 1;
				if (prefix[0][mid] - won[0] >= games || prefix[1][mid] - won[1] >= games) {
					nxt = mid;
					hi = mid - 1;
				} else
					lo = mid + 1;

			}
			if (nxt == -1)
				return -1;
			int win = prefix[0][nxt] - won[0] == games ? 0 : 1;
			++sets[win];
			for (int j = 0; j < 2; j++)
				won[j] = prefix[j][nxt];
			if (nxt == n - 1)
				return sets[win] > sets[1 - win] ? sets[win] : -1;
		}

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt() - 1;
		prefix = new int[2][n];
		for (int j = 0; j < 2; j++) {
			int x = 0;
			for (int i = 0; i < n; i++) {
				if (j == a[i])
					x++;
				prefix[j][i] = x;
			}
		}
		ArrayList<int[]> ans = new ArrayList();
		for (int games = n; games > 0; games--) {
			int s = check(games);
			if (s != -1)
				ans.add(new int[] { s, games });

		}
		Collections.sort(ans, (x, y) -> x[0] != y[0] ? x[0] - y[0] : x[1] - y[1]);

		out.println(ans.size());
		for (int i = 0; i < ans.size(); i++)
			out.println(ans.get(i)[0] + " " + ans.get(i)[1]);
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

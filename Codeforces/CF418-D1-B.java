
/*
 * dp(msk) = min cost to solve the problems represented by ones in the mask
 * We will sort the friends by increasing K, so each time we consider dp(msk), we only that we have as much monitors as the last friend
 * Complexity of dp = 2^m + n
 */
import java.io.*;
import java.util.*;

public class A {

	static long[] memo;
	static long INF = (long) 1e18;
	static ArrayList<Integer>[] adj;

	static long dp(int msk) {
		if (msk == 0)
			return 0;
		if (memo[msk] != -1)
			return memo[msk];
		long ans = INF;
		int idx = log[msk & -msk];
		for (int solve : adj[idx]) {

			int newMsk = msk & (~msks[solve]);
			ans = Math.min(ans, dp(newMsk) + x[solve]);

		}
		return memo[msk] = ans;
	}

	static int[] log, msks, x, k;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt(), b = sc.nextInt();
		x = new int[n];
		Integer[] indices = new Integer[n];
		memo = new long[1 << m];
		k = new int[n];
		log = new int[1 << m];
		log[1] = 0;
		for (int i = 0; i < m; i++)
			log[1 << i] = i;
		adj = new ArrayList[m];

		for (int i = 0; i < m; i++)
			adj[i] = new ArrayList();
		msks = new int[n];
		int all = 0;
		for (int i = 0; i < n; i++) {
			indices[i] = i;
			x[i] = sc.nextInt();
			k[i] = sc.nextInt();
			int M = sc.nextInt(), msk = 0;
			while (M-- > 0) {
				int a = sc.nextInt() - 1;
				msk |= 1 << a;

			}
			msks[i] = msk;
			all |= msk;
		}
		Arrays.sort(indices, Comparator.comparingInt(i -> k[i]));
		if (all + 1 != 1 << m) {
			System.out.println(-1);
			return;
		}
		long ans = 2 * INF;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n && k[indices[j]] == k[indices[i]]; j++) {

				int idx = indices[j];
				int msk = msks[idx];
				while (msk > 0) {
					int x = msk & -msk;
					msk -= x;
					adj[log[x]].add(idx);
				}
				i = j;
			}
			Arrays.fill(memo, -1);
			long dp = dp(all);
			if (dp < INF) {
				ans = Math.min(ans, b * 1L * k[indices[i]] + dp);
			}

		}
		out.println(ans);
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

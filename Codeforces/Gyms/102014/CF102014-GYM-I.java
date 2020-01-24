import java.io.*;
import java.util.*;

public class HiddenTree {

	static int INF = (int) 1e7;

	static int Solve(ArrayList<Integer> a) {

		int ans = 0;
		int tot = 0;
		for (int x : a)
			tot += x;
		int N = (int) 5e5 + 2;
		int[] dp = new int[N + 2];
		Arrays.fill(dp, -INF);
		dp[0] = 0;
		for (int x : a) {
			for (int sum = tot / x * x; sum >= x; sum -= x)
				dp[sum] = Math.max(dp[sum], 1 + dp[sum - x]);
		}
		for (int i = 1; i <= tot; i <<= 1)
			ans = Math.max(ans, dp[i]);
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			int N = 502;
			ArrayList<Integer>[] adj = new ArrayList[N];
			for (int i = 0; i < N; i++)
				adj[i] = new ArrayList();
			while (n-- > 0) {
				int x = sc.nextInt();
				int pow = 1;
				while (x % 2 == 0) {
					x /= 2;
					pow <<= 1;

				}
				adj[x].add(pow);

			}
			int ans = 1;
			for (int i = 1; i < N; i++) {
				if (adj[i].size() > 1)
					ans = Math.max(ans, Solve(adj[i]));
			}
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

	static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	static void shuffle(int[] a) {
		int n = a.length;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int tmpIdx = rand.nextInt(n);
			int tmp = a[i];
			a[i] = a[tmpIdx];
			a[tmpIdx] = tmp;
		}
	}

}

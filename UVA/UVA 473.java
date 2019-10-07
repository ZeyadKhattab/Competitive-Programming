import java.io.*;
import java.util.*;

public class RaucousRockers {

	static int[] time;
	static int t;
	static int[][][] memo;

	static int dp(int idx, int remDisks, int remTime) {
		if (idx == time.length || remDisks == 0)
			return 0;
		if (memo[idx][remDisks][remTime] != -1)
			return memo[idx][remDisks][remTime];
		int ans = dp(idx, remDisks - 1, t);
		ans = Math.max(ans, dp(idx + 1, remDisks, remTime));
		if (time[idx] <= remTime) {
			ans = Math.max(ans, dp(idx + 1, remDisks, remTime - time[idx]) + 1);
		}
		return memo[idx][remDisks][remTime] = ans;

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {

			int n = sc.nextInt();
			t = sc.nextInt();
			int m = sc.nextInt();
			memo = new int[n][m + 1][t + 1];
			for (int[][] x2 : memo)
				for (int[] x : x2)
					Arrays.fill(x, -1);
			time = new int[n];
			String[] s = sc.nextLine().split(", ");
			for (int i = 0; i < n; i++) {
				time[i] = Integer.parseInt(s[i]);
			}
			out.println(dp(0, m, t));
			if (tc > 0)
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

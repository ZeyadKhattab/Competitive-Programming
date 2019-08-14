import java.io.*;
import java.util.*;

public class Plagiarism {

	static char[] a, b;
	static int k, INF = (int) 1e9;
	static int[][][][] memo;

	static int dp(int i, int j, int k1, int flag) {
		if (i == a.length && j == b.length)
			return 0;
		if (memo[flag][k1][i][j] != 0)
			return memo[flag][k1][i][j];
		int ans = INF;
		if (k1 == 0 && flag == 0)
			ans = dp(i, j, 0, 1);
		if (k1 >= k)
			ans = Math.min(ans, dp(i + (flag == 0 ? k1 : 0), j + (flag == 1 ? k1 : 0), 0, 0));
		if (flag == 0) // 10
		{
			if (i + k1 < a.length)
				ans = Math.min(ans, dp(i, j, k1 + 1, 0) + 1);
		} else // 01
		{
			if (j + k1 < b.length)
				ans = Math.min(ans, dp(i, j, k1 + 1, 1) + 1);
		}

		// 11
		int i2 = i, j2 = j;
		if (flag == 0)
			i2 += k1;
		else
			j2 += k1;

		for (int len = 1; i2 < a.length && j2 < b.length; len++, i2++, j2++) {
			if (a[i2] != b[j2])
				break;
			if (i2 - i + 1 >= k)
				ans = Math.min(ans, len + dp(i2 + 1, j, j2 - j + 1, 1));
			if (j2 - j + 1 >= k)
				ans = Math.min(ans, len + dp(i, j2 + 1, i2 - i + 1, 0));

		}

		return memo[flag][k1][i][j] = ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		k = sc.nextInt();
		a = sc.next().toCharArray();
		b = sc.next().toCharArray();
		memo = new int[2][101][a.length + 1][b.length + 1];
		out.println(dp(0, 0, 0, 0));
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

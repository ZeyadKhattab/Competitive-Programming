
/*
 * if K>10*9 sum of digits will be less than k so answer is 0, otherwise
 * one can do digit dp keeping track of sum of digits mod k and number mod k
 */
import java.io.*;
import java.util.*;

public class UVA_11361 {

	static char[] s;
	static int[] pow;

	static int k;

	static long[][][][] memo;

	static long dp(int idx, int eq, int digitsMod, int mod) {
		if (idx == -1)
			return digitsMod == 0 && mod == 0 ? 1 : 0;
		if (memo[eq][idx][digitsMod][mod] != -1)
			return memo[eq][idx][digitsMod][mod];
		long ans = 0;
		int curr = s[s.length - 1 - idx] - '0';

		for (int d = 0; d < 10; d++) {
			if (eq == 1 && d > curr)
				break;
			int newEq = eq == 1 && d == curr ? 1 : 0;
			int newMod = d * pow[idx] % k;
			newMod += mod;
			if (newMod >= k)
				newMod -= k;
			ans += dp(idx - 1, newEq, (digitsMod + d) % k, newMod);

		}
		return memo[eq][idx][digitsMod][mod] = ans;

	}

	static long solve(int r) {
		s = (r + "").toCharArray();
		int len = s.length;
		pow = new int[len];
		pow[0] = 1 % k;
		for (int i = 1; i < len; i++)
			pow[i] = 10 * pow[i - 1] % k;
		for (long[][][] x3 : memo)
			for (long[][] x2 : x3)
				for (long[] x : x2)
					Arrays.fill(x, -1);
		return dp(len - 1, 1, 0, 0);

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		memo = new long[2][12][90][90];

		while (tc-- > 0) {
			int l = sc.nextInt(), r = sc.nextInt();
			k = sc.nextInt();
			if (k > 90) {
				out.println(0);
				continue;
			}
			long ans = solve(r) - solve(l - 1);
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

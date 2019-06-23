
/*
 * let dp(l,r) be the min characters to add for making subsequence l,r a regular sequence
 * if s[l]= ) or ] then add an opening bracket + dp(l+1,r)
 * if s[l]= ( or [ then either close it and add dp(l+1,r) or
 * try to match s[l] with any l<i<=r then add dp(l+1,i-1) +dp(i+1,r)
 */
import java.io.*;
import java.util.*;

public class BracketsSequence
 {
	static int[][] memo;
	static PrintWriter out = new PrintWriter(System.out);
	static char[] s;

	static int dp(int l, int r) {
		if (l > r)
			return 0;
		if (memo[l][r] != -1)
			return memo[l][r];
		int ans;
		if (s[l] == ')' || s[l] == ']')
			ans = 1 + dp(l + 1, r);
		else {
			ans = 1 + dp(l + 1, r);
			for (int i = l; i <= r; i++)
				if (s[i] == negate(s[l]))
					ans = Math.min(ans, dp(l + 1, i - 1) + dp(i + 1, r));
		}
		return memo[l][r] = ans;

	}

	static void print(int l, int r) {
		if (l > r)
			return;
		int ans = dp(l, r);

		if (s[l] == ')' || s[l] == ']') {
			out.print(negate(s[l]));
			out.print(s[l]);
			print(l + 1, r);
		} else {
			if (ans == 1 + dp(l + 1, r)) {
				out.print(s[l]);
				out.print(negate(s[l]));
				print(l + 1, r);
				return;
			}
			for (int i = l; i <= r; i++)
				if (s[i] == negate(s[l]) && ans == dp(l + 1, i - 1) + dp(i + 1, r)) {

					out.print(s[l]);
					print(l + 1, i - 1);
					out.print(s[i]);
					print(i + 1, r);
					break;
				}
		}

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		memo = new int[105][105];
		int tc = sc.nextInt();
		while (tc-- > 0) {
			sc.nextLine();
			s = sc.nextLine().toCharArray();
			for (int[] x : memo)
				Arrays.fill(x, -1);
			print(0, s.length - 1);
			out.println();
			if (tc > 0)
				out.println();

		}
		out.close();

	}

	private static Character negate(char c) {
		if (c == ')')
			return '(';
		if (c == '(')
			return ')';
		if (c == '[')
			return ']';
		return '[';
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

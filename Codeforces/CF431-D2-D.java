
/*
 * for a range [n+1,2*n], the number of number of numbers with exactly k ones increases, so we will find the answer
 * by binary search with using dp(r) = number of numbers between 1 and r that has k ones, so if n is a solution
 * dp(2*n) - dp(n) must be equal m
 */

import java.io.*;
import java.util.*;

public class RandomTask {

	static long num;
	static int k;
	static long[][][] memo;

	static long solve(long n) {
		long l = n + 1, r = 2 * n;
		return get(r) - get(l - 1);
	}

	static long get(long r) {
		int len = Long.toBinaryString(r).length();
		num = r;
		memo = new long[2][len][k + 1];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < len; j++)
				Arrays.fill(memo[i][j], -1);
		long ans = dp(len - 1, 0, k);
		return ans;
	}

	static long dp(int idx, int less, int rem) {
		if (idx == -1)
			return rem == 0 ? 1 : 0;
		if (memo[less][idx][rem] != -1)
			return memo[less][idx][rem];
		int bit = ((1L << idx) & num) == 0 ? 0 : 1;
		long ans = dp(idx - 1, less | bit, rem);
		if (rem > 0 && (bit == 1 || less == 1))
			ans += dp(idx - 1, less, rem - 1);
		return memo[less][idx][rem] = ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		long m = sc.nextLong();
		k = sc.nextInt();
		long lo = 1, hi = (long) 1e18;
		while (lo <= hi) {
			long mid = lo + hi >> 1;
			long cnt = solve(mid);
			if (cnt == m) {
				System.out.println(mid);
				return;
			}
			if (cnt > m)
				hi = mid - 1;
			else
				lo = mid + 1;
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

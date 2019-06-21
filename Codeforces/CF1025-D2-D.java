import java.io.*;
import java.util.*;

public class RecoveringBST {

	static int[] a, gcd[];
	static int[][][] memo;

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static int dp(int l, int r, int root) {

		if (l == r)
			return 1;
		int left = dp_new(l, root - 1, 1), right = dp_new(root + 1, r, 0);
		return left & right;

	}

	static int dp_new(int l, int r, int side) { // if side=0 root=l-1 if side=1 root=r+1
		if (l > r)
			return 1;
		if (memo[side][l][r] != -1)
			return memo[side][l][r];
		int root = side == 0 ? l - 1 : r + 1;
		int ans = 0;
		for (int newRoot = l; newRoot <= r && ans == 0; newRoot++) {
			if (gcd[newRoot][root] > 1)
				ans |= dp(l, r, newRoot);
		}
		return memo[side][l][r] = ans;

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt();
		gcd = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				gcd[i][j] = gcd(a[i], a[j]);
		int ans = 0;
		memo = new int[2][n][n];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < n; j++)
				Arrays.fill(memo[i][j], -1);
		for (int root = 0; root < n && ans == 0; root++)
			ans |= dp(0, n - 1, root);
		out.println(ans == 1 ? "Yes" : "No");
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

import java.io.*;
import java.util.*;

public class Inversions {

	static long[][] dp;

	static int[] a, bit, unkownIndices;

	static long[][] sum;
	static int curr, sz;

	static long bro(int idx, int cnt, int k) {
		if (cnt == 0)
			return 0;
		int l = idx, r = idx + cnt - 1;
		long ans = sum[k][r];
		if (l > 0)
			ans -= sum[k][l - 1];
		return ans;
	}

	static void solve(int l, int r, int optL, int optR, int k) {
		if (l > r)
			return;
		int mid = l + r >> 1;
		int opt = optL;
		long ans = 0;
		for (int cnt = optL; cnt <= optR && cnt + mid < sz; cnt++) {
			long after = sz - 1 - (cnt + mid);
			long cand = dp[curr ^ 1][cnt + mid] + bro(mid, cnt, k) + after * cnt;
			if (cand > ans) {
				ans = cand;
				opt = cnt;
			}
		}
		dp[curr][mid] = ans;
		solve(l, mid - 1, opt, optR, k);
		solve(mid + 1, r, optL, opt, k);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), K = sc.nextInt();
		a = new int[n];

		unkownIndices = new int[n + 1];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			if (a[i] == 0)
				unkownIndices[sz++] = i;
		}
		sum = new long[K + 1][sz];
		bit = new int[K + 1];
		sz = 0;
		long init = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] != 0) {
				init += query(a[i] + 1, K);
			} else {
				for (int k = 1; k < K; k++)
					sum[k][sz] += query(k + 1, K);
				sz++;
			}
			if (a[i] != 0)
				update(a[i], 1);
		}
		Arrays.fill(bit, 0);
		for (int i = n - 1; i >= 0; i--) {
			if (a[i] == 0) {
				for (int k = K; k > 1; k--)
					sum[k][sz - 1] += query(1, k - 1);
				sz--;
			} else
				update(a[i], 1);
		}

		for (int i = 0; i < n; i++)
			if (a[i] == 0)
				sz++;

		for (int k = 1; k <= K; k++) {
			for (int i = 1; i < sz; i++) {

				sum[k][i] += sum[k][i - 1];
			}
		}
		dp = new long[2][sz + 1];
		int nonones = 0;
		sz = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] == 0)
				dp[1][sz++] = nonones;
			else if (a[i] != 1)
				nonones++;
		}
		unkownIndices[sz++] = n;

		for (int i = sz - 2; i >= 0; i--)
			dp[1][i] += dp[1][i + 1];
		curr = 1;
		for (int k = 2; k <= K; k++) {
			curr ^= 1;
			solve(0, sz - 2, 0, n, k);
		}
		out.println(init + dp[K & 1][0]);

		out.close();

	}

	static int query(int idx) {
		int ans = 0;
		while (idx > 0) {
			ans += bit[idx];
			idx -= idx & -idx;
		}
		return ans;
	}

	static int query(int l, int r) {
		return query(r) - query(l - 1);
	}

	static void update(int idx, int v) {
		while (idx < bit.length) {
			bit[idx] += v;
			idx += idx & -idx;
		}
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

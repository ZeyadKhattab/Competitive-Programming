
/*
 * For each shop i, we can find highest selling price
 * by finding the shop j with min r[j]+(j-i)*d[i] for j>=i
 * we can find this using CHT by adding n lines where line i will have slope i
 * and then we can just add i*d[i] to each selling price
 */
import java.io.*;
import java.util.*;

public class SwordProfit {

	static int MOD = (int) 1e9 + 7, size;
	static long INF = (long) 1e17;
	static line[] hull;

	static class line {
		int m, c;

		line(int mm, int cc) {
			m = mm;
			c = cc;
		}

		long eval(long x) {
			return m * x + c;
		}

		double intersect(line l) {
			return (l.c - c) * 1.0 / (m - l.m);
		}
	}

	static int pow(long b, int e) {
		long ans = 1;
		while (e > 0) {
			if (e % 2 == 1)
				ans = ans * b % MOD;
			e >>= 1;
			b = b * b % MOD;
		}
		return (int) ans;
	}

	static void insert(line line) {
		while (size >= 2 && bad(hull[size - 2], hull[size - 1], line)) {
			size--;
		}
		hull[size++] = line;
	}

	private static long query(int x) {
		int ans = 0;
		int lo = 0, hi = size - 2;
		while (lo <= hi) {
			int mid = lo + hi >> 1;
			if (hull[mid].eval(x) <= hull[mid + 1].eval(x)) {
				ans = mid + 1;
				lo = mid + 1;
			} else
				hi = mid - 1;
		}
		return hull[ans].eval(x);
	}

	static boolean bad(line l1, line l2, line l3) {
		// l1 < l2 < l3
		// return true if l2 is no longer on the hull
		return l1.intersect(l2) >= l3.intersect(l2);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), d[] = new int[n], a[] = new int[n], b[] = new int[n], r[] = new int[n];
		long[] q = new long[n];
		for (int i = 0; i < n; i++) {
			q[i] = sc.nextLong();
			a[i] = sc.nextInt();
			b[i] = sc.nextInt();
			r[i] = sc.nextInt();
			d[i] = sc.nextInt();

		}
		hull = new line[n];
		int ans = 0;
		for (int i = n - 1; i >= 0; i--) {
			insert(new line(-i, -r[i]));

			long minR = -query(d[i]);
			minR -= i * 1L * d[i];
			long sellPrice = Math.max(0, q[i] - minR);
			long k = Math.max(0, (sellPrice - a[i]) / b[i]);
			sellPrice %= MOD;
			k %= MOD;
			int x = mult((int) k, (int) sellPrice);
			int buy = mult((int) k, a[i]);
			buy = add(buy, mult(b[i], divide(mult((int) k, (int) k + 1), 2)));
			x = sub(x, buy);
			ans = add(ans, x);

		}
		out.println(ans);
		out.close();

	}

	static int add(int x, int y) {
		int ans = x + y;

		while (ans >= MOD)
			ans -= MOD;
		return ans;
	}

	static int sub(int x, int y) {
		int ans = x - y;
		while (ans < 0)
			ans += MOD;
		return ans;
	}

	static int mult(int x, int y) {
		return (int) (x * 1L * y % MOD);
	}

	static int divide(int x, int y) {
		return mult(x, pow(y, MOD - 2));
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

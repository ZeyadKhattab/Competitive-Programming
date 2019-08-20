
// editorial: http://hsin.hr/coci/archive/2011_2012/ 
// Contest 3
import java.io.*;
import java.util.*;

public class TRAKA {

	static class line {
		long m, c;

		line(long mm, long cc) {
			m = mm;
			c = cc;
		}

		double eval(double x) {
			return m * x + c;
		}

		double intersect(line l) {
			return (l.c - c) * 1.0 / (m - l.m);
		}

	}

	static boolean bad(line l1, line l2, line l3) {
		// l1 < l2 < l3
		// return true if l2 is no longer on the hull
		return l1.intersect(l2) >= l3.intersect(l2);
	}

	static void insert(line line) {
		while (size >= 2 && bad(hull[size - 2], hull[size - 1], line))
			size--;
		hull[size++] = line;
	}

	private static double query(double x) {
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

	static int size;
	static line[] hull;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt();
		int[] t = new int[n], f = new int[m];
		for (int i = 0; i < n; i++)
			t[i] = sc.nextInt();
		for (int j = 0; j < m; j++)
			f[j] = sc.nextInt();
		int[] s = new int[n];
		for (int i = 0; i < n; i++) {
			s[i] += t[i];
			if (i > 0)
				s[i] += s[i - 1];
		}
		hull = new line[n];
		for (int j = 0; j + 1 < n; j++)
			insert(new line(s[j + 1], -s[j]));
		long ans = s[n - 1] * 1L * f[m - 1];
		for (int i = 0; i + 1 < m; i++) {
			long max = t[0] * f[i];
			double x = f[i] * 1.0 / f[i + 1];
			long max2 = Math.round(query(x) * f[i + 1]);
			ans += Math.max(max, max2);

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

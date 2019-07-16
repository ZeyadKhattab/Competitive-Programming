import java.io.*;
import java.util.*;

public class K_Anonymous_Sequence {

	static long INF = (long) 1e17;

	static class line implements Comparable<line> {
		long m, c;

		line(long mm, long cc) {
			m = mm;
			c = cc;
		}

		long eval(long x) {
			return m * x + c;
		}

		double intersect(line l) {
			return (l.c - c) * 1.0 / (m - l.m);
		}

		public int compareTo(line o) {
			// TODO Auto-generated method stub
			return m < o.m ? -1 : m == o.m ? 0 : 1;
		}
	}

	static TreeSet<line> hull;

	static long query(int x) {
		while (hull.size() >= 2) {
			line first = hull.pollFirst();
			if (first.eval(x) > hull.first().eval(x)) {
				hull.add(first);
				break;
			}
		}
		return hull.first().eval(x);
	}

	static boolean bad(line l1, line l2, line l3) {
		// l1 < l2 < l3
		// return true if l2 is no longer on the hull
		return l1.intersect(l2) >= l3.intersect(l2);
	}

	static void insert(line line) {
		line sameSlope = hull.ceiling(line);
		if (sameSlope != null && sameSlope.m == line.m) {
			line.c = Math.max(line.c, sameSlope.c);
			hull.remove(sameSlope);
		}
		line below = hull.lower(line), above = hull.higher(line);
		if (below != null && above != null && bad(below, line, above)) // line not on the hull
			return;

		hull.add(line);
		// remove lines above line
		while (above != null) {
			line aboveAbove = hull.higher(above);
			if (aboveAbove == null || !bad(line, above, aboveAbove))
				break;
			hull.remove(above);
			above = aboveAbove;
		}
		// remove line below line
		while (below != null) {
			line belowBelow = hull.lower(below);
			if (belowBelow == null || !bad(belowBelow, below, line))
				break;
			hull.remove(below);
			below = belowBelow;
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		hull = new TreeSet();
		while (tc-- > 0) {
			hull.clear();
			int n = sc.nextInt(), k = sc.nextInt();
			int[] a = new int[n + 1];
			long[] prefix = new long[n + 1], dp = new long[n + 1];
			for (int i = 1; i <= n; i++) {
				a[i] = sc.nextInt();
				prefix[i] = a[i] + prefix[i - 1];
				if (i < k)
					dp[i] = INF;
				else 
				{
					int l = i - k;
					line newLine = new line(a[l + 1], -(dp[l] + l * 1L * a[l + 1] - prefix[l]));
					insert(newLine);
					dp[i] = prefix[i] - query(i);
				}
			}
			out.println(dp[n]);
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

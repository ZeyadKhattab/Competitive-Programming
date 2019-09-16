// Applying Pick's theorem https://en.wikipedia.org/wiki/Pick%27s_theorem
// for polygons with integer corners
// A = i+ b/2 -1  => 2*A=2*i + b -2
// A=area , i =number of internal integer points, b=number of boundary integer points
//we can easily find the 2*area by cross product, for boundary points, we can loop over the segments and calculate the slope
//m=dy/dx then divide dx by gcd(dx,dy), we can see that each time x is incremented by gcd, corresponding y is integer
// knowing A,b we can calculate number of internal points as (2*A+2-b)/2
import java.io.*;
import java.util.*;

public class TreesOnMyIsland {

	static long area(Point[] poly) { // double area
		long ans = 0;
		for (int i = 0; i + 1 < poly.length; i++)
			ans += poly[i].cross(poly[i + 1]);
		return Math.abs(ans);
	}

	static int lattice(Point a, Point b) {

		if (a.x == b.x) {
			return Math.abs(a.y - b.y);
		}
		int dx = Math.abs(a.x - b.x), dy = Math.abs(a.y - b.y);
		int gcd = gcd(dx, dy);
		dx /= gcd;
		dy /= gcd;
		return Math.abs(a.x - b.x) / dx;

	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			Point[] poly = new Point[n + 1];
			for (int i = 0; i < n; i++)
				poly[i] = new Point(sc.nextInt(), sc.nextInt());
			poly[n] = poly[0];
			long area = area(poly);
			long boundary = 0;
			for (int i = 0; i + 1 < poly.length; i++) {
				boundary += lattice(poly[i], poly[i + 1]);
			}
			// 2A = 2*i + b-2;
			long interior = (area + 2 - boundary) / 2;
			out.println(interior);

		}
		out.close();

	}

	static class Point {
		int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		long cross(Point p) {
			return x * 1L * p.y - y * 1L * p.x;
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

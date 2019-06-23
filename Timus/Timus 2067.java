import java.io.*;
import java.util.*;

public class FriendsAndBerries {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		Point[] pts = new Point[n];
		for (int i = 0; i < n; i++)
			pts[i] = new Point(sc.nextInt(), sc.nextInt());
		int ans = 1;
		Point vector = pts[0].getVector(pts[1]);
		for (int i = 1; i < n; i++) {
			Point v2 = pts[0].getVector(pts[i]);
			if (!vector.collinear(v2))
				ans = 0;
		}
		out.println(ans);
		if (ans == 1) {
			int minIdx = 0, maxIdx = 0;
			for (int i = 1; i < n; i++) {
				if (pts[i].x < pts[minIdx].x || (pts[i].x == pts[minIdx].x && pts[i].y < pts[minIdx].y))
					minIdx = i;
				if (pts[i].x > pts[maxIdx].x || (pts[i].x == pts[maxIdx].x && pts[i].y > pts[maxIdx].y))
					maxIdx = i;
			}
			out.println(minIdx + 1 + " " + (maxIdx + 1));
		}
		out.close();

	}

	static class Point {
		int x, y;

		Point(int a, int b) {
			x = a;
			y = b;
		}

		Point getVector(Point to) {
			return new Point(to.x - x, to.y - y);
		}

		boolean collinear(Point a) {
			long cross = x * 1L * a.y - y * 1L * a.x;
			return cross == 0;
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

}

/*
 * Rotate, translate by minX, minY 
 * then multiply x by W/maxX and multiply y by H/maxY
 */
import java.io.*;
import java.util.*;

public class PerfectPolygons {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int a = sc.nextInt(), w = sc.nextInt(), h = sc.nextInt(), n = sc.nextInt();
		Point[] poly = new Point[n];
		for (int i = 0; i < n; i++)
			poly[i] = new Point(sc.nextInt(), sc.nextInt());

		for (int i = 0; i < n; i++) {
			poly[i] = poly[i].rotate(a);
		}
		double minX = Double.MAX_VALUE, minY = minX;
		for (Point p : poly) {
			minX = Math.min(minX, p.x);
			minY = Math.min(minY, p.y);

		}
		Point translate = new Point(-minX, -minY);
		for (int i = 0; i < n; i++)
			poly[i] = poly[i].translate(translate);
		double max = Double.MIN_VALUE;
		for (Point p : poly)
			max = Math.max(p.x, max);
		for (Point p : poly)
			p.x *= w / max;
		max = Double.MIN_VALUE;
		for (Point p : poly)
			max = Math.max(p.y, max);
		for (Point p : poly)
			p.y *= h / max;
		for (Point p : poly) {
			out.printf("%.8f %.8f\n", p.x, p.y);
		}
		out.close();

	}

	static class Point {
		double x, y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		Point rotate(double angle) {

			angle = Math.toRadians(angle);
			double c = Math.cos(angle), s = Math.sin(angle);
			Point ans = new Point(x * c - y * s, x * s + y * c);

			return ans;
		}

		Point translate(Point p) {
			return new Point(x + p.x, y + p.y);
		}
	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

	}

}


/*
 * If line between the 2 points does not intersect the circle, then the ans is the distance between them.
 * To know whether the line intersects the circle or not, we will use 
 * https://cp-algorithms.com/geometry/circle-line-intersection.html
 * Otherwise, we will find the 4 tangency points (using vector transformations)
 * and add their distance between the 2 points
 * and the arc that connects them
 */
import java.io.*;
import java.util.*;

public class RopeCrisisInRopeLand {

	static double EPS = 1e-9;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			Point[] pts = new Point[2];
			for (int i = 0; i < 2; i++) {
				pts[i] = new Point(sc.nextDouble(), sc.nextDouble());
			}

			double ans;
			line line = new line(pts[0], pts[1]);
			double r = sc.nextDouble();
			Point[] intersect = line.getInserction(r);

			boolean intersection = intersect.length == 2 && intersect[0].between(pts[0], pts[1])
					&& intersect[1].between(pts[0], pts[1]);
			if (!intersection) {
				ans = pts[0].dist(pts[1]);
			} else {
				Point[][] t = new Point[2][];
				for (int i = 0; i < 2; i++) {
					t[i] = pts[i].getTangencyPoint(r);
				}
				ans = pts[0].dist(t[0][0]) + pts[1].dist(t[1][0]);
				double angle = 1000;
				for (int i = 0; i < 2; i++)
					for (int j = 0; j < 2; j++) {
						angle = Math.min(angle, Point.angle(t[0][i], t[1][j]));
					}
				ans += angle * r;
			}

			out.printf("%.3f\n", ans);

		}
		out.close();

	}

	static class line {
		double a, b, c;

		line(Point p1, Point p2) {
			if (Math.abs(p1.x - p2.x) < EPS) { // vertical x = p1.x
				b = 0;
				a = 1;
				c = -p1.x;
			} else {
				b = 1;
				double dy = p1.y - p2.y, dx = p1.x - p2.x;
				a = -dy / dx;
				c = -a * p1.x - p1.y;
			}
		}

		Point[] getInserction(double r) {
			double x0 = -a * c / (a * a + b * b), y0 = -b * c / (a * a + b * b);
			if (c * c > r * r * (a * a + b * b) + EPS)
				return new Point[0];
			else if (Math.abs(c * c - r * r * (a * a + b * b)) < EPS) {
				return new Point[] { new Point(x0, y0) };
			} else {
				double d = r * r - c * c / (a * a + b * b);
				double mult = Math.sqrt(d / (a * a + b * b));
				double ax, ay, bx, by;
				ax = x0 + b * mult;
				bx = x0 - b * mult;
				ay = y0 - a * mult;
				by = y0 + a * mult;

				return new Point[] { new Point(ax, ay), new Point(bx, by) };
			}
		}
	}

	static class Point {
		double x, y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		boolean between(Point p, Point q) {
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x) && y < Math.max(p.y, q.y) + EPS
					&& y + EPS > Math.min(p.y, q.y);
		}

		double dist(Point p) {
			double dx = p.x - x, dy = p.y - y;
			return Math.sqrt(dx * dx + dy * dy);
		}

		public String toString() {
			return x + " " + y;
		}

		static double angle(Point p1, Point p2) { // angle between 2 vectors p1 and p2
			return Math.acos(p1.dot(p2) / (p1.norm() * p2.norm()));
		}

		double dot(Point p) {
			return x * p.x + y * p.y;
		}

		double norm() {
			return Math.sqrt(x * x + y * y);
		}

		Point translate(Point a) {
			return new Point(x + a.x, y + a.y);
		}

		Point rotate(double angle) {
			double c = Math.cos(angle), s = Math.sin(angle);
			return new Point(x * c - y * s, x * s + y * c);
		}

		Point scale(double s) {
			return new Point(x * s, y * s);
		}

		Point[] getTangencyPoint(double r) {
			// a circle centred at (0,0) with radius r
			// external point = this
			double d = norm();
			double angle = Math.acos(r / d); // angle with centre because tangent is perpendicular to radius
			// translating point p to the circumference of the circle
			Point translate = new Point(-x, -y).scale((d - r) / d);
			Point p = this.translate(translate); // translated point
			return new Point[] { p.rotate(angle), p.rotate(-angle) };

		}

//		Point[] getTangencyPoint(double r) {
//			// a circle centred at (0,0) with radius r
//			// external point = (p,q) tangency point = (a,b)
//			// b^2 * (q^2 + p^2) - 2*q*(r^2)*b + r^2(r^2-p^2) =0
//			// solve for b
//			// a= (r^2 - q*b) / p
//			Point[] ans = new Point[2];
//			double p = x, q = y;
//			if (p == 0) {
//				double b = r * r / q;
//				double a;
//				a = Math.sqrt(r * r - b * b);
//				return new Point[] { new Point(a, b), new Point(-a, b) };
//			}
//			double[] b = solveQuadratic(q * q + p * p, -2 * q * r * r, r * r * (r * r - p * p));
//			for (int i = 0; i < 2; i++) {
//				double a = (r * r - q * b[i]) / p;
//				ans[i] = new Point(a, b[i]);
//			}
//			return ans;
//		}
//
//		// ax^2 +b*x + c =0;
//		static double[] solveQuadratic(double a, double b, double c) {
//			double discriminant = b * b - 4 * a * c;
//			if (discriminant < 0)
//				return null;
//			discriminant = Math.sqrt(discriminant);
//			double ans1 = (-b + discriminant) / (2 * a);
//			double ans2 = (-b - discriminant) / (2 * a);
//			return new double[] { ans1, ans2 };
//		}
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

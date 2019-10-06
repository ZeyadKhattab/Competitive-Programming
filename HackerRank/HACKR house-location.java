
import java.io.*;
import java.util.*;

public class HouseLocation {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		double[] r = new double[2], R = new double[2];
		Point[] c = new Point[2];
		for (int i = 0; i < 2; i++)
			r[i] = sc.nextInt();
		for (int i = 0; i < 2; i++) {
			Point a = new Point(sc.nextInt(), sc.nextInt()), b = new Point(sc.nextInt(), sc.nextInt());
			double g = (-a.x + r[i] * r[i] * b.x) / (1 - r[i] * r[i]);
			double f = (-a.y + r[i] * r[i] * b.y) / (1 - r[i] * r[i]);
			double cc = (a.x * a.x + a.y * a.y - r[i] * r[i] * (b.x * b.x + b.y * b.y)) / (1 - r[i] * r[i]);
			c[i] = new Point(-g, -f);
			R[i] = Math.sqrt(g * g + f * f - cc);
		}
		Circle[] circles = new Circle[2];
		for (int i = 0; i < 2; i++)
			circles[i] = new Circle(c[i], R[i]);
		Point[] intersection = circles[0].getIntserection(circles[1]);
		if (intersection == null || intersection.length == 0) {
			System.out.println("Impossible!");
			return;
		}
		Arrays.sort(intersection);
		out.printf("%.2f %.2f\n", Math.abs(intersection[0].x) < EPS ? 0 : intersection[0].x,
				Math.abs(intersection[0].y) < EPS ? 0 : intersection[0].y);
		out.close();
	}

	static double EPS = 1e-9;

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

		line(double a, double b, double c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		// line circle (centre = (0,0)) intersection
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

	static class Point implements Comparable<Point> {
		double x, y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		boolean equals(Point p) {
			return Math.abs(x - p.x) < EPS && Math.abs(y - p.y) < EPS;
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

		@Override
		public int compareTo(Point p) {
			if (Math.abs(x - p.x) > EPS)
				return Double.compare(x, p.x);
			if (Math.abs(y - p.y) < EPS)
				return 0;
			return Double.compare(y, p.y);

		}

	}

	static class Circle {
		Point centre;
		double r;

		Circle(Point centre, double r) {
			this.centre = centre;
			this.r = r;
		}

		Point[] getIntserection(Circle circle) {
			if (centre.equals(circle.centre)) {
				if (r != circle.r) {
					return null;
				}
				// coincide over each other
				return new Point[] { new Point(centre.x - r, centre.y) };

			}
			double x2 = circle.centre.x - centre.x, y2 = circle.centre.y - centre.y;
			double A = -2 * x2, B = -2 * y2, C = sq(x2) + sq(y2) + sq(r) - sq(circle.r);
			line line = new line(A, B, C);
			Point[] ans = line.getInserction(r);
			for (int i = 0; i < ans.length; i++)
				ans[i] = ans[i].translate(centre);
			return ans;

		}

		static double sq(double x) {
			return x * x;
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

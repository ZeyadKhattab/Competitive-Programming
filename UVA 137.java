/*
 * Find the points that are contained inside the other polygon as well as the
 * points of intersection, find the area described by these points
 */
import java.io.*;
import java.util.*;

public class Polygons {

	static double EPS = 1e-9;

	static double solve(Polygon[] poly) {
		double[] area = new double[2];
		ArrayList<Point> newPolygon = new ArrayList();

		for (int i = 0; i < 2; i++) {
			area[i] = area(poly[i].pts);
			for (Point p : poly[i ^ 1].pts)
				if (poly[i].inside(p))
					newPolygon.add(p);
		}
		Point[] curr = poly[0].pts, other = poly[1].pts;
		for (int i = 0; i + 1 < curr.length; i++) {
			Segment currSegment = new Segment(curr[i], curr[i + 1]);
			for (int j = 0; j + 1 < other.length; j++) {
				Segment otherSegment = new Segment(other[j], other[j + 1]);
				Point[] intersectionPoints = currSegment.intersect(otherSegment);
				for (Point p : intersectionPoints) {
					newPolygon.add(p);
				}
			}
		}
		Point[] tmp = new Point[newPolygon.size()];
		int idx = 0;
		for (Point p : newPolygon)
			tmp[idx++] = p;
		tmp = Polygon.convexHull(tmp);
		return area[0] + area[1] - 2 * area(tmp);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			Polygon[] poly = new Polygon[2];
			poly[0] = new Polygon(sc, n);
			int m = sc.nextInt();
			poly[1] = new Polygon(sc, m);
			out.printf("%8.2f", solve(poly));
		}
		out.println();
		out.close();

	}

	static double area(Point[] pts) // clockwise/anti-clockwise check, for convex/concave polygons
	{
		double area = 0.0;
		for (int i = 0; i < pts.length - 1; ++i)
			area += pts[i].x * pts[i + 1].y - pts[i].y * pts[i + 1].x;
		return Math.abs(area) / 2.0; // negative value in case of clockwise
	}

	static class Polygon {
		// Cases to handle: collinear points, polygons with n < 3

		static final double EPS = 1e-9;

		Point[] pts; // first point = last point, counter-clockwise representation

		Polygon(Point[] o) {
			pts = o;
		}

		Polygon(Scanner sc, int n) throws IOException {
			pts = new Point[n + 1];
			for (int i = 0; i < n; i++)
				pts[i] = new Point(sc.nextInt(), sc.nextInt());
			pts[n] = pts[0];
		}

		boolean inside(Point p) // for convex/concave polygons - winding number algorithm
		{
			double sum = 0.0;
			for (int i = 0; i < pts.length - 1; ++i) {
				double angle = Point.angle(pts[i], p, pts[i + 1]);
				if ((Math.abs(angle) < EPS || Math.abs(angle - Math.PI) < EPS) && p.between(pts[i], pts[i + 1]))
					return true;
				if (Point.ccw(p, pts[i], pts[i + 1]))
					sum += angle;
				else
					sum -= angle;
			}

			return Math.abs(2 * Math.PI - Math.abs(sum)) < EPS; // abs makes it work for clockwise
		}

		static Point[] convexHull(Point[] points) {
			int n = points.length;
			Arrays.sort(points);
			Point[] ans = new Point[n << 1];
			int size = 0, start = 0;

			for (int i = 0; i < n; i++) {
				Point p = points[i];
				while (size - start >= 2 && !Point.ccw(ans[size - 2], ans[size - 1], p))
					--size;
				ans[size++] = p;
			}
			start = --size;

			for (int i = n - 1; i >= 0; i--) {
				Point p = points[i];
				while (size - start >= 2 && !Point.ccw(ans[size - 2], ans[size - 1], p))
					--size;
				ans[size++] = p;
			}
			if (size < 0)
				size = 0;
			return Arrays.copyOf(ans, size);
		}

	}

	static class Segment {

		Point p, q;

		Segment(Point a, Point b) {
			p = a;
			q = b;
		}

		Point[] intersect(Segment ls) {
			Line l1 = new Line(p, q), l2 = new Line(ls.p, ls.q);
			if (l1.parallel(l2)) {
				if (l1.same(l2)) {
					ArrayList<Point> ans = new ArrayList();
					if (p.between(ls.p, ls.q))
						ans.add(p);
					if (q.between(ls.p, ls.q))
						ans.add(q);
					if (ls.p.between(p, q))
						ans.add(ls.p);
					if (ls.q.between(p, q))
						ans.add(ls.q);
					Point[] tmp = new Point[ans.size()];
					int idx = 0;
					for (Point p : ans)
						tmp[idx++] = p;
					return tmp;
				}
				return new Point[0];
			}
			Point c = l1.intersect(l2);
			return c.between(p, q) && c.between(ls.p, ls.q) ? new Point[] { c } : new Point[0];
		}
	}

	static class Point implements Comparable<Point> {

		static final double EPS = 1e-9;

		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		public int compareTo(Point p) {
			if (Math.abs(x - p.x) > EPS)
				return x > p.x ? 1 : -1;
			if (Math.abs(y - p.y) > EPS)
				return y > p.y ? 1 : -1;
			return 0;
		}

		public double dist(Point p) {
			return Math.sqrt(sq(x - p.x) + sq(y - p.y));
		}

		static double sq(double x) {
			return x * x;
		}

		boolean between(Point p, Point q) {
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x) && y < Math.max(p.y, q.y) + EPS
					&& y + EPS > Math.min(p.y, q.y);
		}

		// returns true if it is on the line defined by a and b
		boolean onLine(Point a, Point b) {
			if (a.compareTo(b) == 0)
				return compareTo(a) == 0;
			return Math.abs(new Vector(a, b).cross(new Vector(a, this))) < EPS;
		}

		boolean onSegment(Point a, Point b) {
			if (a.compareTo(b) == 0)
				return compareTo(a) == 0;
			return onRay(a, b) && onRay(b, a);
		}

		// returns true if it is on the ray whose start point is a and passes through b
		boolean onRay(Point a, Point b) {
			if (a.compareTo(b) == 0)
				return compareTo(a) == 0;
			return new Vector(a, b).normalize().equals(new Vector(a, this).normalize()); // implement equals()
		}

		// returns true if it is on the left side of Line pq
		// add EPS to LHS if on-line points are accepted
		static boolean ccw(Point p, Point q, Point r) {
			return new Vector(p, q).cross(new Vector(p, r)) > 0;
		}

		static boolean collinear(Point p, Point q, Point r) {
			return Math.abs(new Vector(p, q).cross(new Vector(p, r))) < EPS;
		}

		static double angle(Point a, Point o, Point b) // angle AOB
		{
			Vector oa = new Vector(o, a), ob = new Vector(o, b);
			return Math.acos(oa.dot(ob) / Math.sqrt(oa.norm2() * ob.norm2()));
		}

		// Another way: find closest point and calculate the distance between it and p

	}

	static class Line {

		static final double INF = 1e9, EPS = 1e-9;

		double a, b, c;

		Line(Point p, Point q) {
			if (Math.abs(p.x - q.x) < EPS) {
				a = 1;
				b = 0;
				c = -p.x;
			} else {
				a = (p.y - q.y) / (q.x - p.x);
				b = 1.0;
				c = -(a * p.x + p.y);
			}

		}

		Line(Point p, double m) {
			a = -m;
			b = 1;
			c = -(a * p.x + p.y);
		}

		boolean parallel(Line l) {
			return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
		}

		boolean same(Line l) {
			return parallel(l) && Math.abs(c - l.c) < EPS;
		}

		Point intersect(Line l) {
			if (parallel(l))
				return null;
			double x = (b * l.c - c * l.b) / (a * l.b - b * l.a);
			double y;
			if (Math.abs(b) < EPS)
				y = -l.a * x - l.c;
			else
				y = -a * x - c;

			return new Point(x, y);
		}

		Point closestPoint(Point p) {
			if (Math.abs(b) < EPS)
				return new Point(-c, p.y);
			if (Math.abs(a) < EPS)
				return new Point(p.x, -c);
			return intersect(new Line(p, 1 / a));
		}

	}

	static class Vector {

		double x, y;

		Vector(double a, double b) {
			x = a;
			y = b;
		}

		Vector(Point a, Point b) {
			this(b.x - a.x, b.y - a.y);
		}

		Vector scale(double s) {
			return new Vector(x * s, y * s);
		} // s is a non-negative value

		double dot(Vector v) {
			return (x * v.x + y * v.y);
		}

		double cross(Vector v) {
			return x * v.y - y * v.x;
		}

		double norm2() {
			return x * x + y * y;
		}

		Vector reverse() {
			return new Vector(-x, -y);
		}

		Vector normalize() {
			double d = Math.sqrt(norm2());
			return scale(1 / d);
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

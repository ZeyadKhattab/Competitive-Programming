import java.util.*;

public class MEC {
	static double EPS = 1e-9;

	static class Circle {
		Point centre;
		double r;

		Circle(Point centre, double r) {
			this.centre = centre;
			this.r = r;
		}

		boolean inside(Point p) {
			double dx = p.x - centre.x, dy = p.y - centre.y;
			return dx * dx + dy * dy <= r * r + EPS;
		}

		static Circle minCirc(Point[] v) {
			int n = v.length;
			shuffle(v);
			Point p = new Point(0, 0);
			Circle ret = new Circle(p, 0);
			for (int i = 0; i < n; i++)
				if (!ret.inside(v[i])) {
					ret = new Circle(v[i], 0);
					for (int j = 0; j < i; j++)
						if (!ret.inside(v[j])) {
							ret = new Circle(v[i].getHalfPoint(v[j]), v[i].dist(v[j]) / 2);
							for (int k = 0; k < j; k++)
								if (!ret.inside(v[k])) {
									p = Point.bestof3(v[i], v[j], v[k]);
									ret = new Circle(p, p.dist(v[i]));
								}
						}
				}
			return ret;
		}
	}

	static void shuffle(Point[] a) {

		ArrayList<Point> tmp = new ArrayList();
		for (Point p : a)
			tmp.add(p);
		Collections.shuffle(tmp, new Random());
		for (int i = 0; i < a.length; i++)
			a[i] = tmp.get(i);

	}

	static class Point {
		double x, y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		double dist(Point p) {
			double dx = p.x - x, dy = p.y - y;
			return Math.sqrt(dx * dx + dy * dy);
		}

		Point getHalfPoint(Point p) {
			double a = p.x + x, b = p.y + y;
			return new Point(a / 2, b / 2);

		}

		static double dot(Point p, Point q) {
			return p.x * q.x + p.y * q.y;
		}

		static Point center(Point a, Point b, Point c) {
			b = a.getHalfPoint(b);
			c = a.getHalfPoint(c);
			return interline(b, b.translate(rotate90(new Point(b, a))), c, c.translate(rotate90(new Point(c, a))));
		}

		Point translate(Point p) {
			return new Point(x + p.x, y + p.y);
		}

		static Point rotate90(Point p) {
			return new Point(p.y, -p.x);
		}

		static Point interline(Point a, Point b, Point c, Point d) {
			b = new Point(a, b);
			d = new Point(d, c);
			c = new Point(a, c);
			double x1 = cross(c, d), x2 = cross(b, d);
			b = b.scale(x1 / x2);

			return a.translate(b);
		}

		Point scale(double fac) {
			return new Point(x * fac, y * fac);
		}

		static double cross(Point p, Point q) {
			return p.x * q.y - p.y * q.x;
		}

		static Point bestof3(Point a, Point b, Point c) {
			if (dot(new Point(a, b), new Point(a, c)) < EPS)
				return b.getHalfPoint(c);
			if (dot(new Point(b, a), new Point(b, c)) < EPS)
				return a.getHalfPoint(c);
			if (dot(new Point(c, a), new Point(c, b)) < EPS)
				return a.getHalfPoint(b);
			return center(a, b, c);
		}

		Point(Point a, Point b) {
			x = b.x - a.x;
			y = b.y - a.y;
		}
	}

}

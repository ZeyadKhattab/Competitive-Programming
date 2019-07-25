/*
 * Binary search for the maximum height that divdes the area in 2 equal parts
 */
import java.io.*;
import java.util.*;

public class DivideTheLand {

	static double EPS=1e-9;
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			Trapezium x = new Trapezium(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
			double[] ans = x.solve();
			out.printf("Land #%d: %.6f %.6f\n", t, ans[0], ans[1]);
		}

		out.close();

	}

	static class Trapezium {
		Trapezium(int a, int b, int c, int d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
//			double s=(a+b+c+d)/2.0;
//			double testArea=(a+b)*1.0/(a-b) * Math.sqrt((s-a)*(s-b)*(s-b-c)*(s-b-d));
//			System.err.println(testArea);
			getArea();
			getAlpha();
		}
		int a,b,c,d;
		/*   b= -----
		       /     \ 
		 c=   /       \   d
		 *   / theta   \
		 *  a------------
		 */
		
		
		double theta, alpha, h, area;

		double getTheta() {
			theta = cosineRule(c, a - b, d);
			return theta;
		}

		double getAlpha() {
			alpha = cosineRule(d, a - b, c);
			return alpha;
		}

		double getHeight() {
			h = c * Math.sin(getTheta());
			return h;
		}

		double getArea() {
			getHeight();
			return area = (a + b) / 2.0 * h;
		}

		static double cosineRule(double a, double b, double c) {
			double num = a * a + b * b - c * c;
			double den = 2 * a * b;
			return Math.acos(num / den);
		}

		double[] solve() {
			double lo = 0, hi = h;
			double ans = 0;
			for (int iter = 0; iter < 100; iter++) {
				double mid = (lo + hi) / 2;
				double x1 = mid / Math.tan(theta);
				double x2 = a - mid / Math.tan(alpha);
				double x = x2 - x1;
				double test = (a + x) * mid;
				ans = mid;
				if (Math.abs(area - test) < EPS)
					break;
				else if (test > area)
					hi = mid;
				else
					lo = mid;
			}

			return new double[] { ans / Math.sin(theta), ans / Math.sin(alpha) };
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

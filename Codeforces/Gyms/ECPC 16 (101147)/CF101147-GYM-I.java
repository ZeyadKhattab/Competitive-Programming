
/*
 * Let us find for each circle the interval on the x axis such that it is possible
 * for the device to completely contain the circle's coverage area
 * and then we perform line sweep over these intervals and maximise the answer
 */
import java.io.*;
import java.util.*;

public class OnTheWayToThePark {

	static double EPS = 1e-9;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner("walk.in");
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), r = sc.nextInt();
			Circle[] circles = new Circle[n];
			for (int i = 0; i < n; i++) {
				circles[i] = new Circle(sc.nextInt(), sc.nextInt(), sc.nextInt(), i);
				if (circles[i].can(r)) {
					long dx2 = 1L * (r - circles[i].r) * (r - circles[i].r) - 1L * circles[i].y * circles[i].y;
					double dx = Math.sqrt(dx2);
					circles[i].right = circles[i].x + dx;
					circles[i].left = circles[i].x - dx;

				}

			}
			Arrays.sort(circles, (x, y) -> Double.compare(x.left, y.left));
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>(
					(x, y) -> Double.compare(circles[x].right, circles[y].right));
			for (int i = 0; i < n; i++)
				circles[i].idx = i;
			long curr = 0, ans = 0;
			for (Circle circle : circles) {
				if (circle.left > circle.right)
					continue;
				curr += circle.r;
				while (!pq.isEmpty()) {
					int idx = pq.peek();
					if (circles[idx].right < circle.left) {
						curr -= circles[idx].r;
						pq.poll();
					} else
						break;
				}
				ans = Math.max(ans, curr);
				pq.add(circle.idx);
			}
			out.println(ans);
		}

		out.close();

	}

	static double sq(double x) {
		return x * x;
	}

	static class Circle {
		int x, y, r, idx;
		double left, right;

		Circle(int xx, int yy, int rr, int i) {
			x = xx;
			y = yy;
			r = rr;
			idx = i;
			left = 1;
			right = 0;
		}

		boolean can(int r) {
			int c = r - this.r;
			return Math.abs(y) <= c;
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

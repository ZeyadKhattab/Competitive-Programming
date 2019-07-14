import java.io.*;
import java.util.*;

public class CycleRace {

	static class cyclist {
		int speed, lastTime;
		long totalDist;

		void update(int time, int newSpeed) {
			totalDist += (time - lastTime) * 1L * speed;
			lastTime = time;
			speed = newSpeed;
		}
	}

	static boolean bad(line l1, line l2, line l3) {
		// l1 < l2 < l3
		// return true if l2 is no longer on the hull
		return l1.intersect(l2) >= l3.intersect(l2);
	}

	static TreeSet<line> hull;

	static class line implements Comparable<line> {
		int m;
		long c;

		line(int a, long b) {
			m = a;
			c = b;
		}

		long eval(int x) {
			return m * 1L * x + c;
		}

		double intersect(line l) {
			return (l.c - c) * 1.0 / (m - l.m);
		}

		@Override
		public int compareTo(line o) {
			// TODO Auto-generated method stub
			return m - o.m;
		}
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

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), q = sc.nextInt();
		cyclist[] cyclists = new cyclist[n];
		for (int i = 0; i < n; i++) {
			cyclists[i] = new cyclist();
		}
		hull = new TreeSet<>();
		hull.add(new line(0, 0));
		while (q-- > 0) {
			if (sc.nextInt() == 1) {
				int t = sc.nextInt(), idx = sc.nextInt() - 1, speed = sc.nextInt();
				if (cyclists[idx].speed == speed)
					continue;
				cyclists[idx].update(t, speed);
				insert(new line(speed, cyclists[idx].totalDist - speed * 1L * t));
			} else {
				out.println(query(sc.nextInt()));
			}
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

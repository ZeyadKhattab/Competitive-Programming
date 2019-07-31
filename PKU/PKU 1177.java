//https://github.com/MohamedNabil97/CompetitiveProgramming/blob/master/PKU/1177.cpp
import java.io.*;
import java.util.*;

public class Picture {

	static int start = 0, end = 1, N = 10000;
	static int[] vis;

	static int query(int l, int r) {
		int ans = 0;
		for (int i = l; i <= r; i++)
			if (vis[i] == 0)
				ans++;
		return ans;
	}

	static void update(int l, int r, int v) {
		for (int i = l; i <= r; i++)
			vis[i] += v;
	}

	static int sweep(Event[] events) {
		Arrays.sort(events);
		int ans = 0;
		for (Event curr : events) {
			int l = curr.l, r = curr.r;
			if (curr.t == start) {
				ans += query(l, r - 1);
				update(l, r - 1, 1);
			} else {
				update(l, r - 1, -1);
				ans += query(l, r - 1);

			}

		}
		return ans;
	}

	static class Event implements Comparable<Event> {
		int pos, l, r, t;

		Event(int pos, int l, int r, int t) {
			this.pos = pos;
			this.l = l;
			this.r = r;
			this.t = t;

		}

		@Override
		public int compareTo(Event e) {
			if (e.pos != pos)
				return pos - e.pos;
			return t - e.t;
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		Rect[] rectangles = new Rect[n];
		for (int i = 0; i < n; i++)
			rectangles[i] = new Rect(sc);
		vis = new int[2 * N + 5];
		Event[] events = new Event[2 * n];
		for (int i = 0; i < n; i++) {
			events[2 * i] = new Event(rectangles[i].left, rectangles[i].down, rectangles[i].up, start);
			events[2 * i + 1] = new Event(rectangles[i].right, rectangles[i].down, rectangles[i].up, end);

		}

		int ans = sweep(events);

		for (int i = 0; i < n; i++) {
			rectangles[i].swap();
			events[2 * i] = new Event(rectangles[i].left, rectangles[i].down, rectangles[i].up, start);
			events[2 * i + 1] = new Event(rectangles[i].right, rectangles[i].down, rectangles[i].up, end);

		}
		ans += sweep(events);
		out.println(ans);
		out.close();

	}

	static class Rect implements Comparable<Rect> {
		int left, right, down, up;

		Rect(Scanner sc) throws IOException {
			left = sc.nextInt()+N;
			down = sc.nextInt()+N;
			right = sc.nextInt()+N;
			up = sc.nextInt()+N;

		}

		void swap() {
			int tmp = right;
			right = up;
			up = tmp;
			tmp = left;
			left = down;
			down = tmp;
		}

		@Override
		public int compareTo(Rect o) {
			return o.up - up;
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

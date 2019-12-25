import java.io.*;
import java.util.*;

public class Candles {

	static int n;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		n = sc.nextInt();
		int m = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = -sc.nextInt();
		sort(a);
		for (int i = 0; i < n; i++)
			a[i] *= -1;
		SegmentTree st = new SegmentTree();
		for (int i = 0; i < n; i++)
			st.update(i, i, a[i]);
		
		int ans = 0;
		while (m-- > 0) {
			int query = sc.nextInt();
			if (query > n)
				break;
			int val = st.query(query - 1);
			int first = st.query(val, true), last = st.query(val, false);
			st.update(0, first - 1, -1);
			query -= first;
			st.update(last - query + 1, last, -1);
			if (st.query(n - 1) < 0)
				break;
		
			ans++;
		}
		out.println(ans);
		out.close();

	}

	static class SegmentTree {

		int[] val;

		SegmentTree() {
			val = new int[4 * n];
		}

		public void update(int l, int r, int v) {
			update(1, 0, n - 1, l, r, v);

		}

		void update(int node, int tl, int tr, int l, int r, int v) {
			if (tr < l || r < tl)
				return;
			if (tl >= l && tr <= r)
				val[node] += v;
			else {
				int mid = tl + tr >> 1, left = node << 1, right = left | 1;
				propagate(node);
				update(left, tl, mid, l, r, v);
				update(right, mid + 1, tr, l, r, v);

			}
		}

		private void propagate(int node) {
			int v = val[node];
			val[node] = 0;
			int child = node << 1;
			val[child] += v;
			child++;
			val[child] += v;

		}

		public int query(int val, boolean first) {
			int ans = -1;
			int lo = 0, hi = n - 1;
			while (lo <= hi) {
				int mid = lo + hi >> 1;
				int x = query(mid);
				if (x > val)
					lo = mid + 1;
				else if (x < val)
					hi = mid - 1;
				else {
					ans = mid;
					if (first)
						hi = mid - 1;
					else
						lo = mid + 1;
				}
			}
			return ans;
		}

		public int query(int idx) {
			return query(1, 0, n - 1, idx);
		}

		int query(int node, int tl, int tr, int idx) {
			if (tl == tr)
				return val[node];
			int mid = tl + tr >> 1, left = node << 1, right = left | 1;
			propagate(node);
			return idx <= mid ? query(left, tl, mid, idx) : query(right, mid + 1, tr, idx);

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

	static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	static void shuffle(int[] a) {
		int n = a.length;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int tmpIdx = rand.nextInt(n);
			int tmp = a[i];
			a[i] = a[tmpIdx];
			a[tmpIdx] = tmp;
		}
	}

}

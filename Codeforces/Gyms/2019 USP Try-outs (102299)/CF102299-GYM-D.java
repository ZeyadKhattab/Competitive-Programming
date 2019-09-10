import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class D {

	static double get(long a, long b, long v) {
		double ans = a ^ v;
		return ans / (b ^ v);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		long v = 0;
		SegmentTree tree = new SegmentTree(1L << 33);
		while (n-- > 0) {
			char t = sc.next().charAt(0);
			long as = sc.nextLong(), bs = sc.nextLong(), at = sc.nextLong(), bt = sc.nextLong();
			double l = get(as, bs, v), r = get(at, bt, v);
			if (t == 'B') {
				long h = sc.nextLong() ^ v;
				tree.update(h, l, r);
			} else {
				v = tree.query(l, r);
				out.println(v);
			}
		}
		out.close();

	}

	static class SegmentTree {
		node root;
		long n;

		public SegmentTree(long n) {
			root = new node();
			this.n = n;
		}

		void update(long idx, double l, double r) {
			root = update(root, 0, n - 1, idx, l, r);
		}

		static void insert(TreeMap<Double, Double> map, double l, double r) {
			Double below = map.floorKey(l);
			if (below != null && map.get(below) >= r)
				return;
			map.put(l, r);
			while (true) {
				Entry<Double, Double> hi = map.higherEntry(l);
				if (hi == null || hi.getValue() > r)
					break;
				map.remove(hi.getKey());

			}
		}

		node update(node node, long tl, long tr, long idx, double l, double r) {
			if (node == null)
				node = new node();
			insert(node.map, l, r);
			if (tl == tr)
				return node;
			long mid = tl + tr >> 1;
			if (idx <= mid) {
				node.left = update(node.left, tl, mid, idx, l, r);
			} else
				node.right = update(node.right, mid + 1, tr, idx, l, r);
			return node;

		}

		long query(double a, double b) {
			return query(root, 0, n - 1, a, b);
		}

		boolean ok(node node, double a, double b) {
			if (node == null)
				return false;
			Double l = node.map.floorKey(b);
			return l != null && node.map.get(l) >= a;
		}

		long query(node node, long tl, long tr, double a, double b) {

			if (!ok(node, a, b))
				return 0;
			if (tl == tr)
				return tl;

			long mid = tl + tr >> 1;
			long ans = query(node.right, mid + 1, tr, a, b);

			return ans == 0 ? query(node.left, tl, mid, a, b) : ans;

		}
	}

	static class node {
		node left, right;
		TreeMap<Double, Double> map;

		node() {
			map = new TreeMap();
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

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

	}

}

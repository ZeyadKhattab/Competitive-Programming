// Use a trie where each node has a sorted list of the indices in its subtree
import java.io.*;
import java.util.*;

public class B {

	static int MAX = 15;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), q = sc.nextInt();
			Trie trie = new Trie();
			for (int i = 1; i <= n; i++) {
				trie.insert(sc.nextInt(), i);
			}
			trie.sort();
			while (q-- > 0) {
				out.println(trie.query(sc.nextInt(), sc.nextInt(), sc.nextInt()));
			}
		}

		out.close();

	}

	static class node {
		ArrayList<Integer> indices;
		node one, zero;

		node() {
			indices = new ArrayList();
		}

		void createChild(int t) {
			if (t == 0) {
				if (zero == null)
					zero = new node();
			} else if (one == null) {
				one = new node();
			}
		}

		boolean has(int l, int r) {
			int lo = 0, hi = indices.size() - 1;
			while (lo <= hi) {
				int mid = lo + hi >> 1;
				int curr = indices.get(mid);
				if (curr < l) {
					lo = mid + 1;
				} else if (curr <= r)
					return true;
				else
					hi = mid - 1;

			}
			return false;
		}

		void sort() {
			Collections.sort(indices);
			if (one != null)
				one.sort();
			if (zero != null)
				zero.sort();
		}
	}

	static class Trie {
		node root;

		Trie() {
			root = new node();
		}

		void sort() {
			root.sort();
		}

		void insert(int x, int idx) {
			insert(x, root, idx, MAX);
		}

		void insert(int x, node node, int idx, int bit) {
			if (node != root) {
				node.indices.add(idx);
			}
			if (bit == -1)
				return;
			int currBit = ((x & (1 << bit)) == 0) ? 0 : 1;
			node.createChild(currBit);
			insert(x, currBit == 0 ? node.zero : node.one, idx, bit - 1);

		}

		int query(int x, int l, int r) {
			return query(x, l, r, root, MAX);
		}

		int query(int x, int l, int r, node node, int bit) {
			if (bit == -1)
				return 0;
			int best = (x & 1 << bit) == 0 ? 1 : 0;
			node child = best == 0 ? node.zero : node.one;
			if (child != null && child.has(l, r)) {
				return (1 << bit) + query(x, l, r, child, bit - 1);
			} else {
				return query(x, l, r, best == 0 ? node.one : node.zero, bit - 1);

			}
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

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class DiskTree {

	static PrintWriter out = new PrintWriter(System.out);
	static String[] strings;

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner();
		strings = new String[5005];
		TreeMap<String, Integer> map = new TreeMap();
		while (sc.ready()) {
			int n = sc.nextInt();
			Trie trie = new Trie();
			String[][] s = new String[n][];
			map.clear();
			for (int i = 0; i < n; i++) {
				String[] x = sc.next().split("\\\\");
				for (String y : x) {
					map.put(y, 0);
				}
				s[i] = x;
			}
			int id = 0;
			for (String x : map.keySet()) {
				map.put(x, id);
				strings[id++] = x;
			}
			for (int i = 0; i < n; i++) {
				int[] a = new int[s[i].length];
				for (int j = 0; j < a.length; j++)
					a[j] = map.get(s[i][j]);
				trie.insert(a);
			}
			trie.query();
			out.println();
		}
		out.close();

	}

	static class Trie {
		node root;

		Trie() {
			root = new node();
		}

		void insert(int[] x) {
			root.insert(x, 0);
		}

		void query() {
			root.query();
		}
	}

	static class node {
		TreeMap<Integer, node> nodes;

		node() {
			nodes = new TreeMap();
		}

		void insert(int[] x, int idx) {
			if (idx == x.length)
				return;
			int curr = x[idx];
			if (!nodes.containsKey(curr))
				nodes.put(curr, new node());
			nodes.get(curr).insert(x, idx + 1);
		}

		void query() {
			for (Entry<Integer, node> entry : nodes.entrySet()) {
				int s = entry.getKey();
				node node = entry.getValue();
				node.query(s, 0);
			}
		}

		void query(int s, int d) {
			for (int i = 0; i < d; i++)
				out.print(" ");
			out.println(strings[s]);
			for (Entry<Integer, node> entry : nodes.entrySet()) {
				s = entry.getKey();
				node node = entry.getValue();
				node.query(s, d + 1);
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

}

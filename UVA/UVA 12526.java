import java.io.*;
import java.util.*;

public class CellphoneTyping {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			char[][] s = new char[n][];
			Trie trie = new Trie();
			for (int i = 0; i < n; i++) {
				s[i] = sc.next().toCharArray();
				trie.insert(s[i]);
			}
			int all = 0;
			for (char[] x : s) {
				node curr = trie.root;
				boolean first = true;
				for (char c : x) {
					if (curr.edges > 1 || first || curr.end) {
						all++;
					}
					first = false;
					curr = curr.nodes[c - 'a'];
				}
			}
			out.printf("%.2f\n", all * 1.0 / n);
		}
		out.close();

	}

	static class Trie {
		node root;

		Trie() {
			root = new node();
		}

		void insert(char[] x) {
			root.insert(x, 0);
		}

	}

	static class node {
		node[] nodes;
		int edges;
		boolean end;

		node() {
			nodes = new node[26];
		}

		void insert(char[] x, int idx) {
			if (idx == x.length) {
				end = true;
				return;
			}
			int curr = x[idx] - 'a';
			if (nodes[curr] == null) {
				nodes[curr] = new node();
				edges++;
			}
			nodes[curr].insert(x, idx + 1);
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

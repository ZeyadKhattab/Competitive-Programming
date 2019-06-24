import java.io.*;
import java.util.*;

public class PHONELST {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			boolean ok = true;
			Trie trie = new Trie();
			for (int i = 0; i < n; i++) 
				ok &= trie.insert(sc.next().toCharArray());
			out.println(ok ? "YES" : "NO");
		}
		out.close();

	}

	static class Trie {
		node root;

		Trie() {
			root = new node();
		}

		boolean insert(char[] x) {
			return root.insert(x, 0, false);
		}

	}

	static class node {
		node[] nodes;
		int edges;
		boolean end;

		node() {
			nodes = new node[10];
		}

		boolean insert(char[] x, int idx, boolean newNode) {
			if (end)
				return false;
			if (idx == x.length) {
				end = true;
				return newNode;
			}
			int curr = x[idx] - '0';
			if (nodes[curr] == null) {
				nodes[curr] = new node();
				newNode = true;
				edges++;
			}
			return nodes[curr].insert(x, idx + 1, newNode);
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

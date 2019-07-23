import java.io.*;
import java.util.*;

public class CountryDivision {

	static ArrayList<Integer>[] adj;
	static int n, timer, tin[], tout[];

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		n = sc.nextInt();
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++)
			adj[i] = new ArrayList();
		tin = new int[n];
		tout = new int[n];
		for (int i = 1; i < n; i++) {
			int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
			adj[u].add(v);
			adj[v].add(u);
		}
		LCA LCA = new LCA();
		int q = sc.nextInt();
		while (q-- > 0) {
			int[] x = new int[] { sc.nextInt(), sc.nextInt() };
			pair[] pairs = new pair[2];
			for (int i = 0; i < 2; i++) {
				int[] nodes = new int[x[i]];
				for (int j = 0; j < nodes.length; j++)
					nodes[j] = sc.nextInt() - 1;
				pairs[i] = new pair(nodes, LCA);
			}
			out.println(pairs[0].intersect(pairs[1]) || pairs[1].intersect(pairs[0]) ? "NO" : "YES");

		}
		out.close();

	}

	static class pair {
		int root, subTree[];

		pair(int[] nodes, LCA LCA) {
			subTree = nodes;
			root = nodes[0];
			for (int i = 1; i < nodes.length; i++) {
				root = LCA.lca(nodes[i], root);

			}
		}

		boolean isChild(int u, int v) {
			return tin[v] >= tin[u] && tin[v] <= tout[u];
		}

		boolean intersect(pair p) {
			if (!isChild(root, p.root))
				return false;
			for (int leaf : subTree) {
				if (isChild(p.root, leaf))
					return true;
			}
			return false;
		}

	}

	static class LCA {
		static int log = 22;
		int[] level, up[];

		LCA() {
			up = new int[n][log];
			level = new int[n];
			dfs(0, 0);
		}

		int lca(int u, int v) {

			if (level[u] < level[v])
				return lca(v, u);
			for (int i = log - 1; i >= 0; i--) {
				int u2 = up[u][i];
				if (level[u2] >= level[v])
					u = u2;
			}
			if (u == v)
				return u;
			for (int i = log - 1; i >= 0; i--) {
				int u2 = up[u][i], v2 = up[v][i];
				if (u2 != v2) {
					u = u2;
					v = v2;
				}
			}
			return up[u][0];
		}

		void dfs(int u, int p) {

			tin[u] = timer++;
			up[u][0] = p;
			for (int i = 1; i < log; i++)
				up[u][i] = up[up[u][i - 1]][i - 1];
			for (int v : adj[u])
				if (v != p) {
					level[v] = level[u] + 1;
					dfs(v, u);
				}
			tout[u] = timer - 1;

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

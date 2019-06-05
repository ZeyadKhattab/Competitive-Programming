// explanation: https://codeforces.com/blog/entry/67388?#comment-515585
import java.io.*;
import java.util.*;

public class Ehab_and_the_Big_Finale {

	static Scanner sc = new Scanner();

	static ArrayList<Integer> cand;
	static ArrayList<Integer>[] adj;
	static int[] par, level;

	static void dfs(int u, int p) {
		for (int v : adj[u])
			if (v != p) {
				par[v] = u;
				level[v] = level[u] + 1;
				dfs(v, u);
			}
	}

	static void solve(int u, int lvl) {
		if (level[u] == lvl)
			cand.add(u);
		for (int v : adj[u])
			if (level[v] > level[u])
				solve(v, lvl);
	}

	static int query(int u, int t) throws IOException {
		System.out.println((t == 1 ? "d " : "s ") + u);
		return sc.nextInt();

	}

	static void answer(int u) {
		System.out.println("! " + u);
	}

	public static void main(String[] args) throws IOException {
		int n = sc.nextInt();
		adj = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++)
			adj[i] = new ArrayList();
		for (int i = 1; i < n; i++) {
			int u = sc.nextInt(), v = sc.nextInt();
			adj[u].add(v);
			adj[v].add(u);
		}
		par = new int[n + 1];
		level = new int[n + 1];
		cand = new ArrayList();
		dfs(1, -1);
		int lvl = query(1, 1);
		solve(1, lvl);
		while (true) {
			if (cand.size() == 1) {
				answer(cand.get(0));
				return;
			}

			int u = cand.get(cand.size() / 2);
			int d = query(u, 1);
			if (d == 0) {
				answer(u);
				break;
			}
			int lca_level = lvl - d / 2;
			while (level[u] > lca_level)
				u = par[u];
			cand.clear();
			int v = query(u, 2);
			solve(v, lvl);

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

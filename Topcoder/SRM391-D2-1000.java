
/*
 * Build a graph from the grid, find the SCC of the graph and convert into DAG,
 * find the path in the DAG with the greatest marbles using dp
 */
import java.util.*;

public class MarbleCollectionGame {

	static ArrayList<Integer>[] adj, DAG;
	static int counter, SCC;
	static Stack<Integer> stack;
	static int[] dfsNum, dfsLow, scc, sizes, marbles, memo;
	static boolean[] inScc;

	public static int solve() {
		int n = adj.length;
		SCC = 0;
		stack = new Stack();
		dfsNum = new int[n];
		dfsLow = new int[n];
		sizes = new int[n];
		inScc = new boolean[n];
		scc = new int[n];
		for (int i = 0; i < n; i++)
			if (dfsNum[i] == 0)
				SCC(i);
		memo = new int[SCC];
		DAG = new ArrayList[SCC];
		for (int i = 0; i < SCC; i++) {
			memo[i] = -1;
			DAG[i] = new ArrayList();
		}
		for (int i = 0; i < n; i++)
			for (int v : adj[i])
				if (scc[i] != scc[v]) {
					DAG[scc[i]].add(scc[v]);
				}

		return dfs(scc[0]);
	}

	static int dfs(int u) {
		if (memo[u] != -1)
			return memo[u];
		int ans = 0;
		for (int v : DAG[u])
			ans = Math.max(ans, dfs(v));
		return memo[u] = ans + sizes[u];
	}

	public static void SCC(int u) {
		stack.push(u);
		dfsNum[u] = dfsLow[u] = ++counter;
		for (int v : adj[u]) {
			if (dfsNum[v] == 0)
				SCC(v);
			if (!inScc[v])
				dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
		}
		if (dfsLow[u] == dfsNum[u]) {
			int size = 0;
			while (true) {
				int v = stack.pop();
				scc[v] = SCC;
				size += marbles[v];
				inScc[v] = true;
				if (u == v)
					break;
			}
			sizes[SCC] = size;
			SCC++;
		}
	}

	public static int collectMarble(String[] board) {
		int n = board.length, m = board[0].length();
		int[] di = { 1, 0, -1, 0 };
		int[] dj = { 0, 1, 0, -1 };
		adj = new ArrayList[n * m];
		marbles = new int[n * m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int u = get(i, j, m);
				adj[u] = new ArrayList();
				char c = board[i].charAt(j);
				if (c == '#')
					continue;

				marbles[u] = c >= '0' && c <= '9' ? c - '0' : 0;

				for (int k = 0; k < 4; k++) {
					int i2 = i + di[k], j2 = j + dj[k];
					if (i2 < 0 || i2 >= n || j2 < 0 || j2 >= m || board[i2].charAt(j2) == '#')
						continue;
					if (k == 2 && c != 'U')
						continue;
					if (k == 3 && c != 'L')
						continue;
					addEdge(i, j, i2, j2, m);
				}
			}
		}
		return solve();

	}

	static void addEdge(int i1, int j1, int i2, int j2, int m) {
		int u = get(i1, j1, m), v = get(i2, j2, m);
		adj[u].add(v);
	}

	static int get(int i, int j, int m) {
		return i * m + j;
	}

}

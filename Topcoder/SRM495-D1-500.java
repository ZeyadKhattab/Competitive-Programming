
/*
 * When a box is opened, it can be empty, but if it is not, it will identify some other boxes,
 * if one of the boxes is empty, then we are done, otherwise, we can safely open these boxes,
 * so if we imagine the input as adjaceny matrix in graph, opening a node will open all nodes reachable from it,
 * if we contract the graph into a DAG using SCC, then we will have to visit atleast number of roots in DAG,
 * but sometimes we can skip the last root if visiting other roots will cover n-1 nodes
 */
import java.util.*;

public class CarrotBoxes {

	static boolean[][] adj;
	static boolean[] visited, inScc;

	static int counter, SCC;
	static Stack<Integer> stack;
	static int[] dfsNum, dfsLow, scc;

	public static void solve() {
		int n = adj.length;
		SCC = 0;
		stack = new Stack();
		dfsNum = new int[n];
		dfsLow = new int[n];
		inScc = new boolean[n];
		scc = new int[n];
		for (int i = 0; i < n; i++)
			if (dfsNum[i] == 0)
				SCC(i);

	}

	public static void SCC(int u) {
		stack.push(u);
		dfsNum[u] = dfsLow[u] = ++counter;
		for (int v = 0; v < adj.length; v++) {
			if (!adj[u][v])
				continue;
			if (dfsNum[v] == 0)
				SCC(v);
			if (!inScc[v])
				dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
		}
		if (dfsLow[u] == dfsNum[u]) {
			while (true) {
				int v = stack.pop();
				scc[v] = SCC;
				inScc[v] = true;
				if (u == v)
					break;
			}
			SCC++;
		}
	}

	public static double theProbability(String[] graph) {
		int n = graph.length;
		adj = new boolean[n][n];
		visited = new boolean[n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				adj[i][j] = graph[i].charAt(j) == 'Y';
		solve();
		ArrayList<Integer> roots = new ArrayList();
		for (int c = 0; c < SCC; c++) {
			boolean root = true;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (adj[i][j] && scc[j] == c && scc[i] != c)
						root = false;

			if (root) {
				for (int i = 0; i < n; i++)
					if (scc[i] == c) {
						roots.add(i);
						break;
					}
			}
		}

		double ans = roots.size();
		for (int last = 0; last < roots.size(); last++) {
			Arrays.fill(visited, false);
			int x = 0;
			for (int i = 0; i < roots.size(); i++)
				if (i != last)
					x += dfs(roots.get(i));
			if (x == n - 1)
				ans = roots.size() - 1;
		}
		return 1 - (ans / n);
	}

	static int dfs(int u) {
		visited[u] = true;

		int ans = 1;
		for (int v = 0; v < adj.length; v++)
			if (adj[u][v] && !visited[v])
				ans += dfs(v);
		return ans;
	}

}

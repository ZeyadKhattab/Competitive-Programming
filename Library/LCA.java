public class LCA {
	static int n;
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

		up[u][0] = p;
		for (int i = 1; i < log; i++)
			up[u][i] = up[up[u][i - 1]][i - 1];
		for (int v : adj[u])
			if (v != p) {
				level[v] = level[u] + 1;
				dfs(v, u);
			}

	}

}

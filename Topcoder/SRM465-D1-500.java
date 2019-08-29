import java.util.*;

public class GreenWarfare {

	static final int INF = (int) 1e9 + 2;
	static int V, S, T, res[][];
	static ArrayList<Integer>[] adj;
	static int[] ptr, dist;

	static int dinic() {

		int mf = 0;
		while (bfs()) {
			ptr = new int[V];
			int f;
			while ((f = dfs(S, INF)) != 0) {
				mf += f;
			}
		}
		return mf;
	}

	static boolean bfs() {
		dist = new int[V];
		Arrays.fill(dist, -1);
		dist[S] = 0;
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(S);
		while (!q.isEmpty()) {
			int u = q.remove();
			if (u == T)
				return true;
			for (int v : adj[u])
				if (dist[v] == -1 && res[u][v] > 0) {
					dist[v] = dist[u] + 1;
					q.add(v);
				}
		}
		return false;
	}

	static int dfs(int u, int flow) {
		if (u == T)
			return flow;
		for (int i = ptr[u]; i < adj[u].size(); i = ++ptr[u]) {
			int v = adj[u].get(i);
			if (dist[v] == dist[u] + 1 && res[u][v] > 0) {
				int f = dfs(v, Math.min(flow, res[u][v]));
				if (f > 0) {
					res[u][v] -= f;
					res[v][u] += f;
					return f;
				}
			}
		}
		return 0;

	}

	static void addEdge(int u, int v, int c) {
		adj[u].add(v);
		adj[v].add(u);
		res[u][v] = c;
	}

	public static int minimumEnergyCost(int[] canonX, int[] canonY, int[] baseX, int[] baseY, int[] plantX,
			int[] plantY, int energySupplyRadius) {
		V = 2 * baseX.length + plantX.length + 2;
		S = V - 2;
		T = V - 1;
		int offset = baseX.length;
		adj = new ArrayList[V];
		for (int i = 0; i < V; i++)
			adj[i] = new ArrayList();
		res = new int[V][V];
		for (int base = 0; base < baseX.length; base++) {
			int minCannon = INF;
			for (int cannon = 0; cannon < canonX.length; cannon++) {
				int dx = baseX[base] - canonX[cannon], dy = baseY[base] - canonY[cannon];
				minCannon = Math.min(minCannon, dx * dx + dy * dy);
			}
			addEdge(base, base + offset, minCannon);
			addEdge(base + offset, T, INF);
			for (int plant = 0; plant < plantX.length; plant++) {
				int dx = baseX[base] - plantX[plant], dy = baseY[base] - plantY[plant];
				if (dx * dx + dy * dy <= energySupplyRadius * energySupplyRadius)
					addEdge(plant + 2 * baseX.length, base, INF);

			}
		}
		for (int plant = 0; plant < plantX.length; plant++) {
			int minCannon = INF;
			for (int cannon = 0; cannon < canonX.length; cannon++) {
				int dx = plantX[plant] - canonX[cannon], dy = plantY[plant] - canonY[cannon];
				minCannon = Math.min(minCannon, dx * dx + dy * dy);
			}
			addEdge(S, plant + 2 * offset, minCannon);
		}

		return dinic();

	}

}

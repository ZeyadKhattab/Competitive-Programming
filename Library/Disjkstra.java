import java.io.*;
import java.util.*;

public class Disjkstra {

	static long INF = (long) 1e18 + 1;
	static ArrayList<Edge>[] adj;

	static class Edge implements Comparable<Edge> {
		int node;
		long cost;

		Edge(int u, long c) {
			node = u;
			cost = c;
		}

		@Override
		public int compareTo(Edge other) {
			if (cost != other.cost)
				return Long.compare(cost, other.cost);
			return node - other.node;
		}
	}

	static long[] dijkstra(int S) {
		int n = adj.length;
		long[] dist = new long[n];
		Arrays.fill(dist, INF);
		PriorityQueue<Edge> pq = new PriorityQueue();
		pq.add(new Edge(S, dist[S] = 0));
		while (!pq.isEmpty()) {
			Edge curr = pq.poll();
			int u = curr.node;
			long d = curr.cost;
			if (d > dist[u])
				continue;
			for (Edge nxt : adj[u]) {
				int v = nxt.node;
				long total = d + nxt.cost;
				if (total < dist[v])
					pq.add(new Edge(v, dist[v] = total));
			}

		}
		return dist;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt();
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++)
			adj[i] = new ArrayList();
		while (m-- > 0) {
			int u = sc.nextInt() - 1, v = sc.nextInt() - 1, w = sc.nextInt();
			adj[u].add(new Edge(v, w));
			adj[v].add(new Edge(u, w));

		}
		long[] dist = dijkstra(0);

	}

}

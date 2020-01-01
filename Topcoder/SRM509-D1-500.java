import java.util.*;

public class PalindromizationDiv1  {

	static long INF = (long) 1e18;
	static int n = 26;
	static long[][] cost, dist, match, memo;
	static long[] val;

	static char[] s;

	public static int getMinimumCost(String S, String[] operations) {

		s = S.toCharArray();
		dist = new long[n][n];
		cost = new long[2][n];
		memo = new long[s.length][s.length];
		for (int i = 0; i < n; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
			cost[0][i] = cost[1][i] = INF;
		}
		for (int i = 0; i < s.length; i++)
			Arrays.fill(memo[i], -1);

		// parse operations
		for (String op : operations) {
			String[] x = op.split(" ");
			if (x[0].equals("add"))
				cost[0][x[1].charAt(0) - 'a'] = Integer.parseInt(x[2]);
			else if (x.length == 3) {
				cost[1][x[1].charAt(0) - 'a'] = Integer.parseInt(x[2]);
			} else
				dist[x[1].charAt(0) - 'a'][x[2].charAt(0) - 'a'] = Integer.parseInt(x[3]);

		}
		floyd(dist);
		dijkstra(0);
		dijkstra(1);
		match = new long[n][n];
		for (int c1 = 0; c1 < n; c1++)
			for (int c2 = 0; c2 < n; c2++)
				match[c1][c2] = match(c1, c2);
		val = new long[n];
		for (int i = 0; i < n; i++)
			val[i] = val(i);
		long ans = dp(0, s.length - 1);
		return (int) (ans >= INF ? -1 : ans);

	}

	static long val(int c) {
		long ans = cost[1][c];
		for (int insert = 0; insert < n; insert++)
			ans = Math.min(cost[0][insert] + match[c][insert], ans);
		return ans;
	}

	static long match(int c1, int c2) {
		long ans = INF;
		for (int c = 0; c < n; c++)
			ans = Math.min(ans, dist[c1][c] + dist[c2][c]);
		return ans;
	}

	static long dp(int l, int r) {
		if (l >= r)
			return 0;
		if (memo[l][r] != -1)
			return memo[l][r];
		long ans = INF;
		long v;
		v = match[s[l] - 'a'][s[r] - 'a'];
		if (v != INF)
			ans = Math.min(dp(l + 1, r - 1) + v, ans);
		v = val[s[l] - 'a'];
		if (v != INF)
			ans = Math.min(ans, dp(l + 1, r) + v);
		v = val[s[r] - 'a'];
		if (v != INF)
			ans = Math.min(ans, dp(l, r - 1) + v);
		return memo[l][r] = ans;

	}

	static void dijkstra(int idx) {
		PriorityQueue<node> pq = new PriorityQueue<node>();
		for (int i = 0; i < n; i++) {
			long c = cost[idx][i];
			if (c != INF)
				pq.add(new node(i, c));
		}
		while (!pq.isEmpty()) {
			node curr = pq.poll();
			int u = curr.node;
			long c = curr.cost;
			if (c > cost[idx][u])
				continue;
			for (int v = 0; v < n; v++) {
				long totalCost = c + (idx == 0 ? dist[u][v] : dist[v][u]);
				if (totalCost < cost[idx][v])
					pq.add(new node(v, cost[idx][v] = totalCost));

			}
		}
	}

	static class node implements Comparable<node> {
		int node;
		long cost;

		node(int u, long c) {
			node = u;
			cost = c;
		}

		@Override
		public int compareTo(node o) {
			// TODO Auto-generated method stub
			return Long.compare(cost, o.cost);
		}

	}

	static void floyd(long[][] dist) {
		for (int k = 0; k < n; k++)
			for (int u = 0; u < n; u++)
				for (int v = 0; v < n; v++)
					dist[u][v] = Math.min(dist[u][k] + dist[k][v], dist[u][v]);

	}

	

}

import java.io.*;
import java.util.*;

public class FactorsAndMultiples {

	static ArrayList<Integer>[] adjList;
	static int V, S, T, res[][];
	static final int INF = (int) 1e9;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = sc.nextInt();
			}
			int m = sc.nextInt();
			V = n + m + 2;

			res = new int[V][V];
			adjList = new ArrayList[V];
			S = V - 1;
			T = V - 2;
			for (int i = 0; i < V; i++)
				adjList[i] = new ArrayList();
			for (int i = 0; i < n; i++)
				addEdge(S, i, 1);
			for (int j = n; j < n + m; j++) {
				addEdge(j, T, 1);
				int x = sc.nextInt();

				for (int i = 0; i < n; i++)
					if (a[i] != 0 && x % a[i] == 0) {
						addEdge(i, j, 1);

					} else if (x == 0) {
						addEdge(i, j, 1);

					}
			}
			out.printf("Case %d: %d\n", t, MaxFlow.dinic());

		}
		out.close();

	}

	static void addEdge(int u, int v, int c) {
		adjList[u].add(v);
		adjList[v].add(u);
		res[u][v] = c;
	}

	static class MaxFlow {

		static int[] ptr, dist;

		static int dinic() // O(V^2E)
		{
			int mf = 0;
			while (bfs()) {
				ptr = new int[V];
				int f;
				while ((f = dfs(S, INF)) != 0)
					mf += f;
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
				for (int v : adjList[u])
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
			for (int i = ptr[u]; i < adjList[u].size(); i = ++ptr[u]) {
				int v = adjList[u].get(i);
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

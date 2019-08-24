
/*
 * Let the border nodes be a sink and the nodes with banks be connected to a source,
 * check if the max flow is equal to the number of banks
 */
import java.io.*;
import java.util.*;

public class Crimewave {

	static final int INF = (int) 1e9;
	static int V, S, T;
	static ArrayList<Integer>[] adj;
	static int[][] res;
	static int[] p;
	static int[] di = { 1, -1, 0, 0 };
	static int[] dj = { 0, 0, 1, -1 };

	static int maxFlow() {
		int mf = 0;
		while (true) {
			Queue<Integer> q = new LinkedList<Integer>();
			p = new int[V];
			Arrays.fill(p, -1);
			q.add(S);
			p[S] = S;
			while (!q.isEmpty()) {
				int u = q.remove();
				if (u == T)
					break;
				for (int v : adj[u])
					if (res[u][v] > 0 && p[v] == -1) {
						p[v] = u;
						q.add(v);
					}
			}

			if (p[T] == -1)
				break;
			mf += augment(T, INF);
		}
		return mf;
	}

	static int augment(int v, int flow) {
		if (v == S)
			return flow;
		flow = augment(p[v], Math.min(flow, res[p[v]][v]));
		res[p[v]][v] -= flow;
		res[v][p[v]] += flow;

		return flow;
	}

	static void addEdge(int u, int v, int w) {
		adj[u].add(v);
		adj[v].add(u);
		res[u][v] = w;

	}

	static int getNode(int i, int j, int m) {
		return i * m + j;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), m = sc.nextInt(), b = sc.nextInt();
			V = 2 * (n * m) + 2;
			int offset = n * m;
			adj = new ArrayList[V];
			for (int i = 0; i < V; i++)
				adj[i] = new ArrayList();
			res = new int[V][V];
			S = V - 2;
			T = V - 1;
			boolean[][] used = new boolean[n][m];
			for (int k = 0; k < b; k++) {
				int i = sc.nextInt() - 1, j = sc.nextInt() - 1;
				int node = getNode(i, j, m);
				addEdge(S, node, 1);
				used[i][j] = true;

			}
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++) {
					if (i == 0 || i == n - 1 || j == 0 || j == m - 1)
						addEdge(getNode(i, j, m) + offset, T, 1);
					for (int k = 0; k < 4; k++) {
						int i2 = i + di[k], j2 = j + dj[k];
						if (i2 >= 0 && i2 < n && j2 >= 0 && j2 < m && !used[i2][j2])
							addEdge(getNode(i, j, m) + offset, getNode(i2, j2, m), 1);
					}
					addEdge(getNode(i, j, m), getNode(i, j, m) + offset, 1);
				}

			out.println(maxFlow() == b ? "possible" : "not possible");

		}
		out.close();

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

	static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	static void shuffle(int[] a) {
		int n = a.length;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int tmpIdx = rand.nextInt(n);
			int tmp = a[i];
			a[i] = a[tmpIdx];
			a[tmpIdx] = tmp;
		}
	}

}

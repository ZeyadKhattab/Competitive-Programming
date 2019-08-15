
/*
 * Perform dijkstra where each node is popped from the queue at most K times
 */
import java.io.*;
import java.util.*;

public class NotTheBest {

	static long INF = (long) 1e18;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n + m == 0)
				break;
			int S = sc.nextInt() - 1, T = sc.nextInt() - 1, K = sc.nextInt();
			int[][] adj = new int[n][n];
			for (int[] x : adj)
				Arrays.fill(x, -1);
			while (m-- > 0)
				adj[sc.nextInt() - 1][sc.nextInt() - 1] = sc.nextInt();
			int[] nxt = new int[n];
			long[] dist = new long[n];

			PriorityQueue<edge> pq = new PriorityQueue<>();
			pq.add(new edge(S, 0));
			while (!pq.isEmpty()) {
				edge curr = pq.poll();
				int u = curr.u;
				long cost = curr.cost;
				if (nxt[u] == K)
					continue;
				dist[u] = cost;
				nxt[u]++;
				for (int v = 0; v < n; v++) {
					if (adj[u][v] != -1)
						pq.add(new edge(v, cost + adj[u][v]));

				}
			}
			out.println(nxt[T] == K ? dist[T] : -1);
		}
		out.close();

	}

	static class edge implements Comparable<edge> {
		int u;
		long cost;

		edge(int a, long b) {
			u = a;
			cost = b;
		}

		@Override
		public int compareTo(edge o) {
			return Long.compare(cost, o.cost);
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

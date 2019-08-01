
/*
 * Run FloydWarshall twice while keeping track of the first and the second min distance
 */
import java.io.*;
import java.util.*;

public class AlwaysLate {

	static int[][][] adj;

	static void swap(int u, int v) {
		int min = adj[u][v][0], max = adj[u][v][1];
		if (max < min) {
			adj[u][v][0] = max;
			adj[u][v][1] = min;
		}
	}

	static int INF = (int) 1e9;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = 1;
		while (sc.ready()) {
		int n = sc.nextInt(), m = sc.nextInt();
		adj = new int[n][n][2];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				adj[i][j][0] = i == j ? 0 : INF;
				adj[i][j][1] = INF;
			}
		while (m-- > 0) {
			int u = sc.nextInt(), v = sc.nextInt();
			adj[u][v][0] = adj[v][u][0] = sc.nextInt();
		}
		for (int it = 0; it < 2; it++)
			for (int k = 0; k < n; k++) {
				for (int u = 0; u < n; u++)
					for (int v = 0; v < n; v++) {
						for (int k1 = 0; k1 < 2; k1++)
							for (int k2 = 0; k2 < 2; k2++) {
								int x = adj[u][k][k1] + adj[k][v][k2];
								if (x < adj[u][v][1] && x != adj[u][v][0]) {
									adj[u][v][1] = x;
									swap(u, v);
								}
							}

					}
			}

		out.printf("Set #%d\n", tc++);
		int q = sc.nextInt();
		while (q-- > 0) {
			int u = sc.nextInt(), v = sc.nextInt();
			out.println(adj[u][v][1] == INF ? "?" : adj[u][v][1]);
		}

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

}

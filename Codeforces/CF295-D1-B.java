
/*
 *  Solving the queries in reverse order, we are adding nodes
 *  and we can apply floyd where each time the node we go through is the new node we added
 */
import java.io.*;
import java.util.*;

public class GreghAndGraph {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[][] adj = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				adj[i][j] = sc.nextInt();

		int[] x = new int[n];
		for (int i = 0; i < n; i++)
			x[i] = sc.nextInt() - 1;
		ArrayList<Integer> visited = new ArrayList();
		long[] print = new long[n];
		for (int i = n - 1; i >= 0; i--) {
			long ans = 0;
			int y = x[i];
			for (int u = 0; u < n; u++)
				for (int v = 0; v < n; v++)
					adj[u][v] = Math.min(adj[u][y] + adj[y][v], adj[u][v]);
			visited.add(y);
			for (int u : visited)
				for (int v : visited)
					ans += adj[u][v];

			print[i] = ans;

		}
		for (long z : print)
			out.println(z);
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

/*
 * First, assume shortest paths are the edges of the graph
 * if there is a contradiction (there is a shorter path, then there is no answer)
 * otherwise, we will loop over all edges to check whether we can remove it or not
 * using a similar algorithm to floyd
 */
import java.io.*;
import java.util.*;

public class Antifloyd {

	static ArrayList<Integer> ans = new ArrayList();

	static void solve(int[][] adj) {
		int n = adj.length;
		ans.clear();
		for (int u = 0; u < n; u++)
			for (int v = u + 1; v < n; v++) {
				// can I remove u->v
				boolean otherPath = false;
				for (int k = 0; k < n; k++)
					if (k != u && k != v && adj[u][v] == adj[u][k] + adj[k][v])
						otherPath = true;
				if (!otherPath) {
					ans.add(u + 1);
					ans.add(v + 1);
					ans.add(adj[u][v]);
				}
			}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt(), adj[][] = new int[n][n];
			for (int i = 1; i < n; i++)
				for (int j = 0; j < i; j++)
					adj[i][j] = adj[j][i] = sc.nextInt();
			boolean canRelax = false;
			for (int k = 0; k < n; k++)
				for (int u = 0; u < n; u++)
					for (int v = 0; v < n; v++)
						if (adj[u][k] + adj[k][v] < adj[u][v])
							canRelax = true;
			out.printf("Case #%d:\n", t);
			if (canRelax) {
				out.println("Need better measurements.");
			} else {
				solve(adj);
				out.println(ans.size() / 3);
				for (int i = 0; i < ans.size(); i += 3)
					out.println(ans.get(i) + " " + ans.get(i + 1) + " " + ans.get(i + 2));
			}
			out.println();
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

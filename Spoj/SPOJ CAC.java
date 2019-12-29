// Find the product of the length of each cycle
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Cactus {

	static int[] visited;
	static boolean[][] adj;
	static BigInteger ans;
	static int top, stack[];

	static void dfs(int u, int p) {
		visited[u] = 1;
		stack[++top] = u;
		for (int v = 0; v < adj.length; v++)
			if (adj[u][v] && v != p) {
				if (visited[v] == 0)
					dfs(v, u);
				else if (visited[v] == 1) {
					int curr = 0, tmp = top;
					while (true) {
						curr++;
						if (stack[tmp] == v)
							break;
						tmp--;
					}
					ans = ans.multiply(BigInteger.valueOf(curr));
				}

			}

		top--;
		visited[u] = 2;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt(), m = sc.nextInt();
			ans = BigInteger.ONE;
			top = -1;
			stack = new int[n];
			adj = new boolean[n][n];
			visited = new int[n];
			while (m-- > 0) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				adj[u][v] = adj[v][u] = true;
			}
			dfs(0, 0);
			out.printf("Case %d: %s\n", t, ans.toString());
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

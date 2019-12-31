import java.io.*;
import java.util.*;

public class AMR10J {

	static int MOD = (int) 1e9 + 7;
	static int top, stack[];

	static int pow(long b, int e) {
		long ans = 1;
		while (e > 0) {
			if (e % 2 == 1)
				ans = ans * b % MOD;
			e >>= 1;
			b = b * b % MOD;
		}
		return (int) ans;
	}

	static int[] visited;
	static int cycle;
	static ArrayList<Integer>[] adj;

	static int dfs(int u, int p) {
		visited[u] = 1;
		int ans = 1;
		stack[++top] = u;
		for (int v : adj[u]) {
			if (visited[v] == 0)
				ans += dfs(v, u);
			else if (visited[v] == 1 && v != p) {
				int tmp = top;
				while (true) {
					cycle++;
					if (stack[tmp] == v)
						break;
					tmp--;
				}
			}
		}
		top--;
		visited[u] = 2;
		return ans;
	}

	static int getCycle(int n, int k) {
		int ans = pow(k - 1, n);
		if (n % 2 == 0)
			ans += k - 1;
		else
			ans -= k - 1;
		ans += MOD;
		return ans % MOD;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), k = sc.nextInt();
			adj = new ArrayList[n];
			visited = new int[n];
			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList();
			for (int i = 0; i < n; i++) {
				int u = sc.nextInt();
				adj[u].add(i);
				adj[i].add(u);
			}

			long ans = 1;
			stack = new int[n];
			for (int i = 0; i < n; i++) {

				if (visited[i] != 0)
					continue;
				cycle = 0;
				top = -1;
				int size = dfs(i, i);
				if (cycle > 0) {

					long curr = getCycle(cycle, k);
					curr = curr * pow(k - 1, size - cycle) % MOD;
					ans = ans * curr % MOD;
				} else {
					ans = ans * k % MOD * pow(k - 1, size - 1) % MOD;
				}

			}

			out.println(ans);
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

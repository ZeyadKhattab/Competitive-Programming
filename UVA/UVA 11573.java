// 0-1 BFS
import java.io.*;
import java.util.*;

public class OceanCurrents {

	static int n, m;
	static int[][] grid;
	static int INF = (int) 1e9;
	static int[] di = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dj = { 0, 1, 1, 1, 0, -1, -1, -1 };

	static int solve(int iS, int jS, int iT, int jT) {
		Deque<edge> queue = new LinkedList<>();
		int[][] dist = new int[n][m];
		for (int[] x : dist)
			Arrays.fill(x, INF);
		queue.add(new edge(iS, jS, dist[iS][jS] = 0));
		while (!queue.isEmpty()) {
			edge curr = queue.poll();
			int i = curr.i, j = curr.j, c = curr.c;
			if (i == iT && j == jT)
				break;
			for (int k = 0; k <= 7; k++) {
				int i2 = i + di[k], j2 = j + dj[k];
				if (i2 >= 0 && i2 < n && j2 >= 0 && j2 < m) {

					int add = grid[i][j] == k ? 0 : 1;
					int total = c + add;
					if (total < dist[i2][j2] && add == 0)
						queue.addFirst(new edge(i2, j2, dist[i2][j2] = total));
					else if (total < dist[i2][j2] && add == 1)
						queue.addLast(new edge(i2, j2, dist[i2][j2] = total));

				}
			}
		}
		return dist[iT][jT];

	}

	static class edge {
		int i, j, c;

		edge(int a, int b, int x) {
			i = a;
			j = b;
			c = x;
		}

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		n = sc.nextInt();
		m = sc.nextInt();
		grid = new int[n][m];
		for (int i = 0; i < n; i++) {
			char[] x = sc.next().toCharArray();
			for (int j = 0; j < m; j++)
				grid[i][j] = x[j] - '0';
		}
		int q = sc.nextInt();
		while (q-- > 0) {
			out.println(solve(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt() - 1));
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

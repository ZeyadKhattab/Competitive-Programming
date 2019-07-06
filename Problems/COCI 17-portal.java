
//idea: https://github.com/boi-2014/tasks/blob/master/solutions/analysis/portals.tex
import java.io.*;
import java.util.*;

public class portal {
	static int INF = (int) 1e9;
	static int[] di = { 1, -1, 0, 0 };
	static int[] dj = { 0, 0, 1, -1 };

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt();
		int[][] up = new int[n][m], down = new int[n][m], left = new int[n][m], right = new int[n][m];
		int[][] dist = new int[n][m];
		for (int[] x : dist)
			Arrays.fill(x, INF);
		char[][] grid = new char[n][m];
		PriorityQueue<edge> pq = new PriorityQueue<>((x, y) -> x.c - y.c);

		for (int i = 0; i < n; i++) {
			grid[i] = sc.next().toCharArray();
			for (int j = 0; j < m; j++)
				if (grid[i][j] == 'C')
					pq.add(new edge(i, j, dist[i][j] = 0));
		}
		for (int j = 0; j < m; j++) {
			up[0][j] = 0;
			down[n - 1][j] = n - 1;

		}
		for (int i = 0; i < n; i++) {
			left[i][0] = 0;
			right[i][m - 1] = m - 1;
		}
		for (int i = 1; i < n - 1; i++)
			for (int j = 1; j < m - 1; j++) {
				up[i][j] = grid[i][j] == '#' ? i : up[i - 1][j];
				left[i][j] = grid[i][j] == '#' ? j : left[i][j - 1];

			}
		for (int i = n - 2; i > 0; i--)
			for (int j = m - 2; j > 0; j--) {
				down[i][j] = grid[i][j] == '#' ? i : down[i + 1][j];
				right[i][j] = grid[i][j] == '#' ? j : right[i][j + 1];
			}

		int ans = -1;
		while (!pq.isEmpty()) {
			edge curr = pq.poll();
			int i = curr.i, j = curr.j, c = curr.c;
			if (grid[i][j] == 'F') {
				ans = dist[i][j];
				break;
			}
			if (c > dist[i][j])
				continue;
			for (int k = 0; k < 4; k++) {
				int i2 = i + di[k], j2 = j + dj[k];
				if (grid[i2][j2] != '#' && c + 1 < dist[i2][j2])
					pq.add(new edge(i2, j2, dist[i2][j2] = c + 1));
			}
			int min = INF;
			// up
			int i2 = up[i][j] + 1, j2 = j;
			min = Math.min(min, Math.abs(i - i2));

			// down
			i2 = down[i][j] - 1;
			min = Math.min(min, Math.abs(i - i2));

			// left
			i2 = i;
			j2 = left[i][j] + 1;
			min = Math.min(min, Math.abs(j - j2));

			// right
			j2 = right[i][j] - 1;
			min = Math.min(min, Math.abs(j - j2));

			min++;
			// up
			i2 = up[i][j] + 1;
			j2 = j;
			if (c + min < dist[i2][j2])
				pq.add(new edge(i2, j2, dist[i2][j2] = c + min));
			// down
			i2 = down[i][j] - 1;
			if (c + min < dist[i2][j2])
				pq.add(new edge(i2, j2, dist[i2][j2] = c + min));
			// left
			i2 = i;
			j2 = left[i][j] + 1;
			if (c + min < dist[i2][j2])
				pq.add(new edge(i2, j2, dist[i2][j2] = c + min));
			// right
			j2 = right[i][j] - 1;
			if (c + min < dist[i2][j2])
				pq.add(new edge(i2, j2, dist[i2][j2] = c + min));
		}
		out.println(ans == -1 ? "nemoguce" : ans);
		out.close();

	}

	static class edge {
		int i, j, c;

		edge(int a, int b, int C) {
			i = a;
			j = b;
			c = C;
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

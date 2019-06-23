import java.io.*;
import java.util.*;

public class Robbery {

	static boolean[][][] there;

	static boolean can(int i, int j, int t, int dt) {
		boolean[][] x = there[t + dt];
		if (i > 0 && x[i - 1][j])
			return true;
		if (i + 1 < x.length && x[i + 1][j])
			return true;
		if (j > 0 && x[i][j - 1])
			return true;
		if (j + 1 < x[0].length && x[i][j + 1])
			return true;
		return x[i][j];
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		for (int cas = 1;; cas++) {
			int W = sc.nextInt(), H = sc.nextInt(), T = sc.nextInt();
			if (W + H + T == 0)
				break;

			there = new boolean[T][H][W];
			for (int i = 0; i < T; i++)
				for (int j = 0; j < H; j++)
					for (int k = 0; k < W; k++)
						there[i][j][k] = true;
			int n = sc.nextInt();
			while (n-- > 0) {
				int t = sc.nextInt() - 1, l = sc.nextInt() - 1, u = sc.nextInt() - 1, r = sc.nextInt() - 1,
						b = sc.nextInt() - 1;
				for (int i = u; i <= b; i++)
					for (int j = l; j <= r; j++)
						there[t][i][j] = false;

			}
			ArrayList<Integer> ans = new ArrayList();
			boolean escape = false;
			for (int t = T - 1; t >= 0 && !escape; t--) {
				int possible = 0;

				for (int i = 0; i < H; i++)
					for (int j = 0; j < W; j++)
						if (there[t][i][j]) {
							if (t == T - 1 || can(i, j, t, 1)) {
								possible++;

							} else
								there[t][i][j] = false;

						}
				if (possible == 0) {
					escape = true;
				}

			}

			for (int t = 0; t < T && !escape; t++) {
				int possible = 0;
				int I = 0, J = 0;
				for (int i = 0; i < H; i++)
					for (int j = 0; j < W; j++)
						if (there[t][i][j]) {
							if (t == 0 || can(i, j, t, -1)) {
								possible++;
								I = i;
								J = j;
							} else
								there[t][i][j] = false;

						}
				if (possible == 0) {
					escape = true;
				} else if (possible == 1) {
					ans.add(t + 1);
					ans.add(J + 1);
					ans.add(I + 1);

				}
			}
			out.printf("Robbery #%d:\n", cas);
			if (escape) {
				out.println("The robber has escaped.");

			} else if (ans.size() == 0) {
				out.println("Nothing known.");
			} else {
				for (int i = 0; i < ans.size(); i += 3) {
					out.printf("Time step %d: The robber has been at %d,%d.\n", ans.get(i), ans.get(i + 1),
							ans.get(i + 2));
				}
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

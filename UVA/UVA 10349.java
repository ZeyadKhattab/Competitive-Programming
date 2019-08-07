
/*
 * dp(row,msk) = answer for all rows starting from row and downwards given that msk represents
 * which columns from previous rows have antennas pointing downwards.
 * For each row, we can loop over the new msk for this row, any columns not yet covered will be covered 
 * by antennas oriented horizontally, we have to use bitwise operations ans preprocessing to optimize solution
 */
import java.io.*;
import java.util.*;

public class AntennaPlacement {

	static int[][] memo, process;
	static char[][] grid;
	static int[] msks;
	static int INF = (int) 1e9;

	static int dp(int row, int prevMsk) {
		if (row == grid.length)
			return 0;
		if (memo[row][prevMsk] != -1)
			return memo[row][prevMsk];
		int m = grid[row].length;
		int ans = INF;
		for (int currMsk = 0; currMsk < 1 << m; currMsk++) {

			int tmpMsk = msks[row] & currMsk & prevMsk;
			int curr = m - Integer.bitCount(currMsk) + process[m][tmpMsk];
			ans = Math.min(ans, curr + dp(row + 1, currMsk));
		}
		return memo[row][prevMsk] = ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		memo = new int[50][2000];
		msks = new int[50];
		process = new int[11][];
		for (int m = 0; m < process.length; m++) {
			process[m] = new int[1 << m];
			for (int msk = 0; msk < 1 << m; msk++) {
				for (int bit = 0; bit < m; bit++) {
					int curr = (1 << bit & msk) > 0 ? 1 : 0;
					if (curr == 1) {
						process[m][msk]++;
						bit++;
					}
				}
			}
		}

		while (tc-- > 0) {
			int n = sc.nextInt(), m = sc.nextInt();
			grid = new char[n][m];
			for (int i = 0; i < n; i++) {
				grid[i] = sc.next().toCharArray();
				int msk = 0;
				for (int j = 0; j < m; j++) {
					if (grid[i][j] == '*')
						msk |= 1 << j;
				}
				msks[i] = msk;
				Arrays.fill(memo[i], -1);
			}
			out.println(dp(0, (1 << m) - 1));

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

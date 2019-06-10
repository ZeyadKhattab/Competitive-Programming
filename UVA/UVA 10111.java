/*
 * Let solve (msk) be a functions that returns true if player X has a winning position currently and false otherwise,
 * msk will represent the grid using a ternary string (Y=>0, X=>1, emtpyCell=>2) and for X to win, at least one position 
 * must be winning position and if it is y's turn, then there must be no way for y to win
 */
import java.io.*;
import java.util.*;

public class FindtheWinningMove {

	static char getBit(String ternary, int bit) {
		return bit < ternary.length() ? ternary.charAt(ternary.length() - bit - 1) : '0';
	}

	static int[] memo, pow;
	static int N = 81 * 81 * 81 * 81, n = 16;

	static boolean isWinPos(String ternary, char turn) {
		for (int i = 0; i < 4; i++) {
			int ones = 0;
			for (int j = i; j < n; j += 4)
				if (getBit(ternary, j) == turn)
					ones++;
			if (ones == 4)
				return true;

		}
		for (int i = 0; i < n; i += 4) {
			int ones = 0;
			for (int j = i; j < i + 4; j++)
				if (getBit(ternary, j) == turn)
					ones++;
			if (ones == 4)
				return true;

		}
		int ones = 0;
		for (int i = 0; i < n; i += 5)
			if (getBit(ternary, i) == turn)
				ones++;
		if (ones == 4)
			return true;

		ones = 0;
		for (int i = 3; i < 15; i += 3)
			if (getBit(ternary, i) == turn)
				ones++;
		if (ones == 4)
			return true;
		return false;

	}

	static int solve(int msk, int turn) {
		if (memo[msk] != -1)
			return memo[msk];
		String ternary = Integer.toString(msk, 3);
		boolean isWin = isWinPos(ternary, (char) ((1 ^ turn) + '0'));
		if (isWin)
			return memo[msk] = turn == 0 ? 1 : 0;
		int ans;
		if (turn == 1) {
			ans = 0;
			for (int bit = 0; bit < n && ans == 0; bit++) {
				if (getBit(ternary, bit) == '2') {
					ans |= solve(msk - pow[bit], 1 ^ turn);
				}
			}
		} else {
			ans = 1;
			boolean ok = false;
			for (int bit = 0; bit < n && ans == 1; bit++) {
				if (getBit(ternary, bit) == '2') {
					ans &= solve(msk - 2 * pow[bit], 1 ^ turn);
					ok = true;
				}
			}
			if (!ok)
				ans = 0;
		}
		return memo[msk] = ans;
	}


	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		char[][] grid = new char[4][4];
		memo = new int[N];
		pow = new int[N];
		for (int i = 0; i < N; i++) {
			memo[i] = -1;
			if (i > 0)
				pow[i] = 3 * pow[i - 1];
			else
				pow[i] = 1;
		}
		out: while (true) {

			if (sc.next().charAt(0) == '$')
				break;
			int msk = 0;
			for (int i = 0; i < 4; i++) {

				grid[i] = sc.next().toCharArray();
				for (int j = 0; j < 4; j++) {
					if (grid[i][j] == 'x')
						msk += pow[i * 4 + j];
					else if (grid[i][j] == '.')
						msk += 2 * pow[i * 4 + j];
				}

			}

			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++) {
					if (grid[i][j] != '.')
						continue;
					int newMsk = msk - pow[i * 4 + j];
					if (solve(newMsk, 0) == 1) {
						out.printf("(%d,%d)\n",i,j);
						continue out;
					}
				}
			out.println("#####");

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

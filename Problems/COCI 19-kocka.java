/*
 * By processing the rows (left and right), we can know the positions of the first and the last blocked cell for each row,
 * then when we process the columns (top and down), we can know the positions of the first and the last blocked cell for each column
 * and we have to make sure the first position for each column is >= the first time we know that the first position for a row is in this column
 * and similarly for the last position
 */
import java.io.*;
import java.util.*;

public class A {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] L = new int[n];
		for (int i = 0; i < n; i++)
			L[i] = sc.nextInt();
		boolean ok = true;
		int[] first = new int[n], last = new int[n];
		Arrays.fill(first, -1);
		Arrays.fill(last, -1);
		for (int row = 0; row < n; row++) {
			int r = sc.nextInt(), l = L[row];
			if (l == -1 || r == -1) {
				ok &= l == r;
			} else {
				r = n - r - 1;
				if (l > r)
					ok = false;
				if (first[l] == -1)
					first[l] = row;
				last[l] = row;
				if (first[r] == -1)
					first[r] = row;
				last[r] = row;

			}
		}

		int[] U = new int[n];
		for (int i = 0; i < n; i++)
			U[i] = sc.nextInt();
		for (int col = 0; col < n; col++) {
			int d = sc.nextInt(), u = U[col];
			if (d == -1 || u == -1) {
				ok &= u == d;
				ok &= first[col] == -1;
				ok &= last[col] == -1;
			} else {
				d = n - d - 1;
				if (u > d)
					ok = false;
				if (first[col] != -1 && first[col] < u) {
					ok = false;
				}
				if (last[col] != -1 && last[col] > d)
					ok = false;
			}
		}
		out.println(ok ? "DA" : "NE");
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

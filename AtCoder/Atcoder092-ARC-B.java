/*
 * for bit k, T=2^k, kth bit is one if 
 * T<= a[i] + b[j] <=2*T || 3*T<= a[i] + b[j] <=4*T (taken mod 2*T)
 */
import java.io.*;
import java.util.*;

public class TwoSequences {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n], b = new int[n], tmpA = new int[n], tmpB = new int[n];
		for (int i = 0; i < n; i++)
			tmpA[i] = sc.nextInt();

		for (int i = 0; i < n; i++)
			tmpB[i] = sc.nextInt();
		int ans = 0;
		for (int k = 0; k < 30; k++) {
			int T = 1 << k;
			for (int i = 0; i < n; i++) {
				a[i] = tmpA[i] % (2 * T);
				b[i] = tmpB[i] % (2 * T);
			}
			Arrays.sort(a);
			Arrays.sort(b);
			for (int i = 0; i < n / 2; i++) {
				int x = b[n - i - 1];
				b[n - i - 1] = b[i];
				b[i] = x;
			}

			int xor = 0;
			int[] t = new int[5];
			for (int i = 0; i < n; i++) {
				for (int j = 1; j <= 4; j++)
					while (t[j] < n && a[i] + b[t[j]] >= T * j)
						t[j]++;
				int one = Math.max(0, t[1] - t[2]), two = Math.max(0, t[3] - t[4]);
				xor ^= one;
				xor ^= two;
			}
			if ((xor & 1) == 1)
				ans += T;

		}
		out.println(ans);
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

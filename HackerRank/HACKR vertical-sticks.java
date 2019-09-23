
/*
 * For every stick, position and height of horizontal segment,
 * calculate the contribution to the expected value by using binomial coefficient to know 
 * how many subsets for sticks with height less than current stick multiplied by number of sticks
 * taller than current stick
 */
import java.io.*;
import java.util.*;

public class VerticalSticks {

	static double[] fac;

	static double choose(int n, int k) {
		if (k > n)
			return 0;
		return fac[n] / fac[n - k];
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		fac = new double[50 + 1];
		fac[0] = 1;
		for (int i = 1; i <= 50; i++)
			fac[i] = i * fac[i - 1];

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			double ans = 0;
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();
			for (int h : a) {
				int less = 0, greater = 0;
				for (int x : a)
					if (x < h)
						less++;
					else
						greater++;
				greater--;
				for (int pos = 1; pos <= n; pos++) {
					for (int cont = 1; cont < pos; cont++) {
						ans += greater * choose(less, cont - 1) * fac[n - (cont + 1)] * cont;
					}
					ans += choose(less, pos - 1) * fac[n - pos] * pos;

				}

			}
			System.out.printf("%.2f\n", ans / fac[n]);

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

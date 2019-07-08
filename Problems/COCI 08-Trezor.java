import java.io.*;
import java.util.*;

public class trezor {

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static int lcm(int a, int b) {
		return a / gcd(a, b) * b;
	}

	static ArrayList<Integer> getPrimeFactors(int x) {
		ArrayList<Integer> ans = new ArrayList();
		for (int d = 2; d * d <= x; d++) {
			if (x % d == 0) {
				ans.add(d);
				while (x % d == 0)
					x /= d;
			}
		}
		if (x > 1)
			ans.add(x);
		return ans;
	}

	static int remove(ArrayList<Integer> factors, int L) {

		int ans = 0;
		int size = factors.size();
		for (int msk = 1; msk < 1 << size; msk++) {
			int div = 1;
			for (int j = 0; j < size; j++)
				if ((msk & (1 << j)) > 0)
					div = lcm(div, factors.get(j));
			div = L / div;
			if (Integer.bitCount(msk) % 2 == 1)
				ans += div;
			else
				ans -= div;
		}

		return ans;

	}

	static int remove(int x, int L) {

		return remove(getPrimeFactors(x), L);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int B = sc.nextInt() + sc.nextInt(), L = sc.nextInt();
		long[] ans = new long[3];
		for (int dx = 0; dx <= B; dx++) {
			int notA = dx == 0 ? L - 1 : remove(dx, L);
			int notB = dx == B ? L - 1 : remove(B - dx, L);
			int notBoth = dx == 0 ? notB : dx == B ? notA : notA + notB - remove(lcm(dx, B - dx), L);
			ans[0] += notBoth;
			ans[1] += notA + notB - 2 * notBoth;
			ans[2] += L - (notA + notB - notBoth);

		}
		for (long x : ans)
			out.println(x);

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

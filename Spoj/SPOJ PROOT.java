
//https://cp-algorithms.com/algebra/primitive-root.html

import java.io.*;
import java.util.*;

public class PROOT {

	static int powMod(long b, int e, int MOD) {
		long ans = 1;
		while (e > 0) {
			if (e % 2 == 1)
				ans = ans * b % MOD;
			e >>= 1;
			b = b * b % MOD;
		}
		return (int) ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int p = sc.nextInt(), n = sc.nextInt();
			if (p == 0 && n == 0)
				break;
			ArrayList<Integer> primes = new ArrayList();
			int phi = p - 1, x = phi;
			for (int i = 2; i * 1L * i <= x; i++) {
				if (x % i == 0) {
					primes.add(i);
					while (x % i == 0)
						x /= i;
				}
			}
			if (x > 1)
				primes.add(x);

			while (n-- > 0) {
				int r = sc.nextInt();
				boolean one = false;
				for (int factor : primes) {
					one |= powMod(r, phi / factor, p) == 1;
					if (one)
						break;
				}
				out.println(one ? "NO" : "YES");
			}
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

import java.io.*;
import java.util.*;

public class MSKYCODE {

	static int N = (int) 5e5 + 5;

	static long nck(long n, int k) {
		if (k > n)
			return 0;
		long ans = 1;
		for (int i = 0; i < k; i++)
			ans *= (n - i);
		return ans / fac(k);
	}

	static long fac(int n) {
		long ans = 1;
		for (int i = 1; i <= n; i++)
			ans *= i;
		return ans;
	}

	static ArrayList<Integer> getPrimes(int x) {
		ArrayList<Integer> ans = new ArrayList();
		for (int d = 2; d * d <= x; d++) {
			if (x % d == 0) {
				ans.add(d);
				while (x % d == 0)
					x /= d;
			}
		}
		if (x != 1)
			ans.add(x);
		return ans;
	}

	static void update(int x, int[] a) {
		for (int d = 1; d * d <= x; d++) {
			if (x % d != 0)
				continue;
			a[d]++;
			if (d * d != x)
				a[x / d]++;
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			int[] a = new int[n];
			int[] cnt = new int[N];
			for (int i = 0; i < n; i++) {
				a[i] = sc.nextInt();
				if (i < 3) {
					update(a[i], cnt);
				}
			}
			long ans = 0;
			for (int i = 3; i < n; i++) {
				ArrayList<Integer> primes = getPrimes(a[i]);
				long curr = 0;
				int sz = primes.size();
				for (int msk = 1; msk < 1 << sz; msk++) {
					int x = 1;
					for (int j = 0; j < sz; j++)
						if ((msk & 1 << j) != 0)
							x *= primes.get(j);
					int sign = Integer.bitCount(msk) % 2 == 1 ? 1 : -1;
					curr += sign * nck(cnt[x], 3);
				}
				ans += curr;
				update(a[i], cnt);

			}

			out.println(nck(n, 4) - ans);
		}
		out.close();

	}

	static long test(int[] a) {
		long ans = 0;
		int n = a.length;
		for (int i1 = 0; i1 < n; i1++)
			for (int i2 = i1 + 1; i2 < n; i2++)
				for (int i3 = i2 + 1; i3 < n; i3++)
					for (int i4 = i3 + 1; i4 < n; i4++) {
						int gcd = gcd(gcd(a[i1], a[i2]), gcd(a[i3], a[i4]));
						if (gcd == 1)
							ans++;
					}
		return ans;
	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
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


/*
 * Number of permutations = n! / c1! * c2! ,.. c26! where ci is the number of occurences of the ith character
 * to find the last nonzero digit, we will find this number after dividing pairs of 2 and 5
 * after prime factorization and then compute the number while taking mod 10
 */
import java.io.*;
import java.util.*;

public class LastDigit {

	static ArrayList<Integer> primes;
	static boolean[] isPrime;
	static int[] cnt;

	static void sieve(int N) {
		primes = new ArrayList();
		isPrime = new boolean[N + 5];
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;
		for (int i = 2; i < isPrime.length; i++)
			if (isPrime[i]) {
				primes.add(i);
				for (int j = i + i; j < isPrime.length; j += i)
					isPrime[j] = false;
			}
	}

	static void procces(int x, int v) {
		for (int p : primes) {
			if (p > x)
				break;
			long i = p;
			while (i <= x) {
				cnt[p] += v * (x / i);
				i = i * p;
			}
		}
	}

	static int pow(int b, int e, int mod) {
		int ans = 1;
		b %= mod;
		while (e > 0) {
			if (e % 2 == 1)
				ans = ans * b % mod;
			e >>= 1;
			b = b * b % mod;
		}
		return ans;
	}

	static int solve(int[] f, int n) {

		Arrays.fill(cnt, 0);
		procces(n, 1);
		for (int x : f)
			procces(x, -1);
		int ans = 1;
		cnt[2] -= cnt[5];
		cnt[5] = 0;
		for (int p : primes) {
			if (p > n)
				break;
			ans = ans * pow(p, cnt[p], 10);
			ans %= 10;
		}

		return ans;

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int N = (int) 1e6;
		sieve(N);
		cnt = new int[N + 1];
		while (sc.ready()) {
			char[] s = sc.next().toCharArray();
			int[] f = new int[26];
			for (char c : s)
				f[c - 'a']++;
			out.println(solve(f, s.length));
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

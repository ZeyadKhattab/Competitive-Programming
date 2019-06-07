/*
 *  Let g(x) = k*x mod(p). We can notice that g(x) is a bijective function on the domain [0,p[
 *  and the problem can be restated as find the number of functions f such that f(g(x)) = g(f(x))
 *  After constructing a graph where the nodes are the [0,p[ and edge exists between x and g(x)
 *  it can be noticed that each connected component is independent and each node has exactly p options (ignoring node 0)
 *  so the total number of options is p to the power of number of connected components
 */
import java.io.*;
import java.util.*;

public class ModularArithmetic {

	static int MOD = (int) 1e9 + 7;

	static int pow(long b, int e) {
		long ans = 1;
		while (e-- > 0)
			ans = ans * b % MOD;
		return (int) ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int p = sc.nextInt(), k = sc.nextInt();
		if (k == 0) {
			System.out.println(pow(p, p - 1));
			return;
		} else if (k == 1) {
			System.out.println(pow(p, p));
			return;
		}

		int size = 0;
		long curr = 1;
		while (true) {
			curr = curr * k % p;
			size++;
			if (curr == 1)
				break;
		}
		int num = (p - 1) / size;
		out.println(pow(p, num));

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

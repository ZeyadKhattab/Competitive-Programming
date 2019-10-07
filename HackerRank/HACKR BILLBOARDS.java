
/*
 * let dp(i) = ans for first i+1 items, then dp(i) =max (dp(i-1), max for j from i-k+1 to i dp(j-2)+sum(j,i))
 *  =dp(j-2) + sum(i) - sum(j-1), to quickly find the maximum j, we will store them in a deque
 *  where the values are strictly decreasing and the maximum is in the first position 
 */
import java.io.*;
import java.util.*;

public class Billboards {

	static long[] dp, sum;
	static Deque<Integer> deque;
	static int k;

	static void add(int idx) {
		long val = get(idx);
		while (!deque.isEmpty() && val >= get(deque.peekLast())) {
			deque.pollLast();
		}
		deque.addLast(idx);
	}

	static long query(int idx) {
		while (deque.peekFirst() < idx - k + 1)
			deque.pollFirst();

		return get(deque.getFirst());
	}

	static long get(int idx) { // dp[idx-2]-sum[idx-1]
		long ans = idx >= 2 ? dp[idx - 2] : 0;
		ans -= idx >= 1 ? sum[idx - 1] : 0;
		return ans;

	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		k = sc.nextInt();
		deque = new LinkedList<Integer>();
		sum = new long[n];
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			sum[i] += a[i];
			if (i > 0)
				sum[i] += sum[i - 1];
		}
		dp = new long[n];
		for (int i = 0; i < n; i++) {
			add(i);
			if (i > 0)
				dp[i] = dp[i - 1];
			dp[i] = Math.max(dp[i], sum[i] + query(i));

		}
		out.println(dp[n - 1]);
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

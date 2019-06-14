
/*
 * For each i, we will find the first number to the left and to the right such that a[j]%a[i]>0,
 * we will do this using a stack as described here: https://csacademy.com/lesson/stack_application_soldiers_row
 */
import java.io.*;
import java.util.*;

public class PairOfNumbers {

	static int[] get(int[] a) {
		int n = a.length;
		Stack<Integer> stack = new Stack();
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			int x = a[i];
			while (!stack.isEmpty() && a[stack.peek()] % x == 0)
				stack.pop();
			ans[i] = stack.empty() ? i : i - stack.peek() - 1;
			stack.push(i);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), a[] = new int[n], b[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			b[n - 1 - i] = a[i];
		}
		int[] l = get(a), r = get(b);
		int max = 0;
		TreeSet<Integer> ans = new TreeSet();
		for (int i = 0; i < n; i++) {
			int cand = l[i] + 1 + r[n - 1 - i];
			if (cand > max)
				ans.clear();
			if (cand >= max) {
				max = cand;
				ans.add(i - l[i] + 1);
			}
		}
		out.println(ans.size() + " " + (max - 1));
		for (int x : ans)
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


/*
 * Postfix Expression using stack is equivalent to dfs traversal of expression tree, 
 * while using queue is equivalent to bfs traversal
 */
import java.io.*;
import java.util.*;

public class Expressions {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			char[] s = sc.next().toCharArray();
			int n = s.length;
			int[] left = new int[n], right = new int[n];
			Arrays.fill(left, -1);
			Arrays.fill(right, -1);
			Stack<Integer> stack = new Stack();
			for (int i = 0; i < n; i++) {
				char c = s[i];
				if (c >= 'A' && c <= 'Z') {
					int b = stack.pop(), a = stack.pop();
					left[i] = a;
					right[i] = b;

				}
				stack.push(i);
			}
			ArrayList<Character> ans = new ArrayList();
			Queue<Integer> q = new LinkedList();
			q.add(n - 1);
			while (!q.isEmpty()) {
				int curr = q.poll();
				ans.add(s[curr]);
				if (left[curr] == -1)
					continue;
				q.add(left[curr]);
				q.add(right[curr]);
			}
			for (int i = n - 1; i >= 0; i--)
				out.print(ans.get(i));
			out.println();
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

	}

}

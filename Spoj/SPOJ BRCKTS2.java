
/*
 * We will try to build a graph where an edge from u to v
 * represents that a bracket sequence starting at v is nested inside a bracket sequence starting at u
 * ,we will only care about direct nestings
 * To do that, iterate over the string, and whenever we reach a closing bracket , we will add an edge
 * between it and all opening brackets greater than or equal its corresponding open bracket,
 * Once we have this graph, the width of a sequence is summation width of children + 2 + children -1 (spaces between them)
 * and height is max height of children + 1 
 */

import java.io.*;
import java.util.*;

public class BRCKTS2 {

	static ArrayList<Integer>[] adj;
	static int[] w, h;

	static long dfs(int u, int sign) {
		w[u] = adj[u].size() - 1 + 2;
		long ans = 0;
		for (int v : adj[u]) {
			ans += dfs(v, -sign);
			w[u] += w[v];
			h[u] = Math.max(h[v], h[u]);

		}
		h[u]++;
		ans += sign * (h[u] * 1L * w[u]);
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			char[] s = sc.next().toCharArray();
			int n = s.length;
			adj = new ArrayList[n];
			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList();
			Stack<Integer> stack = new Stack();
			boolean[] root = new boolean[n];
			w = new int[n];
			h = new int[n];
			PriorityQueue<Integer> children = new PriorityQueue(Collections.reverseOrder());
			for (int i = 0; i < s.length; i++) {
				if (s[i] == '(') {
					stack.add(i);
					root[i] = true;
				} else {
					int open = stack.pop();
					while (!children.isEmpty() && children.peek() >= open) {
						int child = children.poll();
						adj[open].add(child);
						root[child] = false;
					}
					children.add(open);
				}
			}
			long ans = 0;
			for (int i = 0; i < n; i++)
				if (root[i])
					ans += dfs(i, 1);
			out.println(ans);
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

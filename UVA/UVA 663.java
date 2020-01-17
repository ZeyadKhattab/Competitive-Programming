// Find an arbitrary mathcing, for each edge in the matching, if the MCBM is not equal to n, this edge is unique
import java.io.*;
import java.util.*;

public class SortingSlides {

	static int n, match[];
	static ArrayList<Integer>[] adjList;
	static boolean[] vis;

	static int go() {
		int matches = 0;
		match = new int[n];
		Arrays.fill(match, -1);
		for (int i = 0; i < n; ++i) {
			vis = new boolean[n];
			matches += aug(i);
		}
		return matches;
	}

	static int aug(int u) // returns 1 if an augment path is found
	{
		vis[u] = true;
		for (int v : adjList[u])
			if (match[v] == -1 || !vis[match[v]] && aug(match[v]) == 1) {
				match[v] = u;
				return 1;
			}
		return 0;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1;; t++) {
			n = sc.nextInt();
			if (n == 0)
				break;
			out.printf("Heap %d\n", t);
			int[][] cord = new int[n][4];
			adjList = new ArrayList[n];
			for (int i = 0; i < n; i++) {

				adjList[i] = new ArrayList();
				for (int j = 0; j < 4; j++)
					cord[i][j] = sc.nextInt();
			}
			for (int j = 0; j < n; j++) {
				int x = sc.nextInt(), y = sc.nextInt();
				for (int i = 0; i < n; i++) {
					if (x >= cord[i][0] && x <= cord[i][1] && y >= cord[i][2] && y <= cord[i][3]) {
						adjList[i].add(j);
					}
				}
			}
		
			if (go() != n) {
				out.println("none\n");
				continue;
			}
			int cnt = 0;
			int[] matchOriginal = match.clone();
			int[] ans = new int[n];
			for (int j = 0; j < n; j++) {
				int i = matchOriginal[j];
				ans[i] = -1;
				for (int idx = 0;; idx++)
					if (adjList[i].get(idx) == j) {
						adjList[i].remove(idx);
						break;
					}
				if (go() != n) {
					cnt++;
					ans[i] = j;
				}
				adjList[i].add(j);

			}
			if (cnt==0)
				out.println("none");
			else {
				boolean first=true;
				for (int i = 0; i < n; i++) {
					if(ans[i]==-1)
						continue;
					if (!first)
						out.print(" ");
					else
						first=false;
					out.printf("(%c,%d)", (char) (i + 'A'), ans[i] + 1);
				}
				out.println();
			}
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

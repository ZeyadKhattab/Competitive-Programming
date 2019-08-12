
/*
 * There will be an optimal solution that starts when someone leaves. Why? 
 * Because if there is an interval where no one leaves, then we can shift the starting position of this
 *  interval one slot to the right, the length will decrease and the answer will not
 *  Using this observation, we can try all possible starts and binary search for the end which satisfies the problem
 *  using a prefix sum for the left border
 */
import java.io.*;
import java.util.*;

public class School Reunion {

	static int INF = (int) 1e9;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt(), p = sc.nextInt(), l[] = new int[n];
			Integer r[] = new Integer[n];
			TreeMap<Integer, Integer> map = new TreeMap();
			for (int i = 0; i < n; i++) {
				l[i] = sc.nextInt();
				r[i] = sc.nextInt();
				map.put(l[i], i);
				map.put(r[i], i);

			}
			int[] unmap = new int[2 * n];
			int id = 0;
			for (int x : map.keySet()) {
				map.put(x, id);
				unmap[id++] = x;
			}
			int[] prefix = new int[2 * n];

			for (int i = 0; i < n; i++)
				prefix[map.get(l[i])]++;
			for (int i = 1; i < 2 * n; i++)
				prefix[i] += prefix[i - 1];
			Arrays.sort(r);
			int curr = 0, ans = INF;
			for (int v : r) {

				int lo = map.get(v), hi = 2 * n - 1, best = -1;
				while (lo <= hi) {
					int mid = lo + hi >> 1;
					if (prefix[mid] - curr >= p) {
						best = mid;
						hi = mid - 1;
					} else
						lo = mid + 1;
				}
				if (best != -1) {
					ans = Math.min(ans, unmap[best] - v);
				}
				curr++;

			}
			out.printf("Case %d: %d\n", t, ans);

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


// using persistent segment tree
import java.io.*;
import java.util.*;

public class MAX_XOR {

	static int N = 1 << 15, MAX = 15;
	static int MAXN = (int) 1e5;
	static int currNode, left[], right[], sum[], roots[];

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		sum = new int[(N << 2) + 16 * MAXN];
		left = new int[(N << 2) + 16 * MAXN];
		right = new int[(N << 2) + 16 * MAXN];

		while (tc-- > 0) {
			currNode = 0;
			int n = sc.nextInt(), q = sc.nextInt();
			roots = new int[n + 1];
			roots[0] = build(0, N);
			for (int i = 1; i <= n; i++) {
				int x = sc.nextInt();
				roots[i] = update(x, 1, roots[i - 1]);
			}
			while (q-- > 0) {
				int x = sc.nextInt(), l = sc.nextInt(), r = sc.nextInt(), ans = 0;
				for (int bit = MAX; bit >= 0; bit--) {

					int need = (x & 1 << bit) == 0 ? 1 : 0;
					if (need == 0) {
						int zeroes = query(l, r, ans, ans + (1 << bit) - 1);

						if (zeroes == 0)
							ans += 1 << bit;

					} else {

						int ones = query(l, r, ans + (1 << bit), ans + (1 << (bit + 1)) - 1);

						if (ones > 0)

							ans += 1 << bit;

					}

				}
				out.println(x ^ ans);

			}
		}
		out.close();

	}

	static int query(int l, int r, int a, int b) { // how many numbers between a and b in the indices l and r
		return query(a, b, roots[r]) - query(a, b, roots[l - 1]);
	}

	static int update(int idx, int v, int old) {
		return update(0, N, idx, v, old);
	}

	static int update(int tl, int tr, int idx, int v, int old) {
		if (tl == tr) {
			sum[currNode] = sum[old] + v;
			return currNode++;
		}
		int mid = tl + tr >> 1;
		int ans = currNode++;
		if (idx <= mid) {
			right[ans] = right[old];
			left[ans] = update(tl, mid, idx, v, left[old]);
		} else {
			left[ans] = left[old];
			right[ans] = update(mid + 1, tr, idx, v, right[old]);
		}
		sum[ans] = sum[left[ans]] + sum[right[ans]];
//		ans.sum = ans.left.sum + ans.right.sum;
		return ans;

	}

	static int build(int tl, int tr) {

		int ans = currNode++;
		if (tl == tr)
			return ans;
		int mid = tl + tr >> 1;
		left[ans] = build(tl, mid);
		right[ans] = build(mid + 1, tr);
		return ans;
	}

	static int query(int l, int r, int node) {
		return query(0, N, l, r, node);
	}

	static int query(int tl, int tr, int l, int r, int node) {
		if (r < tl || tr < l)
			return 0;
		if (tl >= l && tr <= r)
			return sum[node];
		int mid = tl + tr >> 1;
		return query(tl, mid, l, r, left[node]) + query(mid + 1, tr, l, r, right[node]);
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

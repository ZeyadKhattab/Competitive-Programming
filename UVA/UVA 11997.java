import java.io.*;
import java.util.*;

public class KSmallestSums {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			int[] a1 = new int[n], a2 = new int[n];
			for (int i = 0; i < n; i++)
				a1[i] = sc.nextInt();
			PriorityQueue<pair> pq = new PriorityQueue<pair>((x, y) -> x.sum - y.sum);
			for (int i = 1; i < n; i++) {
				pq.clear();
				for (int j = 0; j < n; j++)
					a2[j] = sc.nextInt();
				Arrays.sort(a2);
				int min = a2[0];
				for (int j = 0; j < n; j++) {
					pq.add(new pair(a1[j] + min, 0));
				}
				for (int j = 0; j < n; j++) {
					pair curr = pq.poll();
					int idx = curr.idx, sum = curr.sum;
					a1[j] = sum;
					if (idx + 1 < n)
						pq.add(new pair(sum - a2[idx] + a2[idx + 1], idx + 1));
				}

			}
			out.print(a1[0]);
			for (int i = 1; i < n; i++)
				out.print(" " + a1[i]);
			out.println();
		}

		out.close();

	}

	static class pair {
		int sum, idx;

		pair(int x, int y) {
			sum = x;
			idx = y;
		}
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

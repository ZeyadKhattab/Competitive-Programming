import java.io.*;
import java.util.*;

public class Tourist {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner("tourist.txt");
		PrintWriter pw = new PrintWriter("output.txt");
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt(), k = sc.nextInt();
			long v = sc.nextLong();
			int start = (int) (((v - 1) * k) % n);
			String[] attractions = new String[n];
			for (int i = 0; i < n; i++)
				attractions[i] = sc.nextLine();
			pw.printf("Case #%d: ", t);
			int end = (start + k - 1) % n;
			if (end < start) {
				boolean first = true;
				for (int i = 0; i <= end; i++) {
					if (first)
						first = false;
					else
						pw.print(" ");
					pw.print(attractions[i]);
				}
				for (int i = start; i < n; i++) {
					if (first)
						first = false;
					else
						pw.print(" ");
					pw.print(attractions[i]);
				}
			}

			else {
				boolean first = true;

				for (int i = start; i <= end; i++) {
					if (first)
						first = false;
					else
						pw.print(" ");
					pw.print(attractions[i]);
				}
			}
			pw.println();
		}

		pw.close();
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public Scanner(String s) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(s));
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}

}

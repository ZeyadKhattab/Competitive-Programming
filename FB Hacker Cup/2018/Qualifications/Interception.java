import java.io.*;
import java.util.*;

public class Interception {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner("interception.txt");
		PrintWriter pw = new PrintWriter("output.txt");
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			pw.printf("Case #%d: ", t);
			int n = sc.nextInt();
			if (n % 2 == 1)
				pw.print("1\n0.0\n");
			else
				pw.print("0\n");
			while (n-- > -1)
				sc.nextInt();

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

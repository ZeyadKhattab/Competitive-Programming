import java.io.*;
import java.util.*;

public class EthanSearchingForAString {

	static boolean ethan(String a, String b) {
		int i = 0, j = 0;
		while (true) {

			if (i >= a.length())
				return true;
			if (j >= b.length())
				return false;
			if (a.charAt(i) == b.charAt(j)) {
				i++;
				j++;
			} else {
				if (i == 0)
					j++;
				i = 0;
			}
		}
	}

	public static void main(String[] args) throws IOException {


		Scanner sc = new Scanner("ethan_searches_for_a_string.txt");
		PrintWriter pw = new PrintWriter("output.txt");
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			pw.printf("Case #%d: ", t);
			String a = sc.nextLine();
			int[] f = new int[26];
			int[] l = new int[26];
			Arrays.fill(f, -1);
			int n = a.length();
			String ans = "Impossible";
			for (int i = 0; i < n; i++) {
				int c = a.charAt(i) - 'A';
				if (f[c] == -1)
					f[c] = i;
				else if (l[c] == 0)
					l[c] = i;

			}

			for (int c = 0; c < 26; c++) {
				if (l[c] == 0)
					continue;

				StringBuilder sb = new StringBuilder(a.substring(0, l[c]));
				sb.append(a);
				if (!ethan(a, sb.toString())) {
					ans = sb.toString();
					break;
				}

			}

			pw.println(ans);
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

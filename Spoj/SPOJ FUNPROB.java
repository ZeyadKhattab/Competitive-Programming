/*
 * total number of sequences is (n+m) choose m
 * we need to count bad sequences, assume that each sequence is path in a grid, 
 * each 10$ ticket moves in y direction, and 5$ moves in x direction, and we start at 0,0
 * so bad paths end at (m,n) and intersects with  line y=x+1
 * after reflecting the part of the path after intersecting y=x+1, we know that number of these bad paths
 * = (n+m) choose (m+1)
 * https://en.wikipedia.org/wiki/Catalan_number#Second_proof
 */
import java.io.*;
import java.util.*;

public class FUNPROB  {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n == 0 && m == 0)
				break;
			out.printf("%.6f\n", n > m ? 0 : (m + 1 - n) * 1.0 / (m + 1));
		}
		out.close();

	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

	}

}

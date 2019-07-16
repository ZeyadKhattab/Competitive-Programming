import java.io.*;
import java.util.*;

public class MachineWorks {

	static class line implements Comparable<line> {
		long m, c;

		line(long mm, long cc) {
			m = mm;
			c = cc;
		}

		long eval(long x) {
			return m * x + c;
		}

		double intersect(line l) {
			return (l.c - c) * 1.0 / (m - l.m);
		}

		public int compareTo(line o) {
			// TODO Auto-generated method stub
			return Long.compare(m, o.m);
		}
	}

	static TreeSet<line> hull;

	static long query(int x) {
		while (hull.size() >= 2) {
			line first = hull.pollFirst();
			if (first.eval(x) > hull.first().eval(x)) {
				hull.add(first);
				break;
			}
		}
		return hull.first().eval(x);
	}

	static boolean bad(line l1, line l2, line l3) {
		// l1 < l2 < l3
		// return true if l2 is no longer on the hull
		return l1.intersect(l2) >= l3.intersect(l2);
	}

	static void insert(line line) {
		line sameSlope = hull.ceiling(line);
		if (sameSlope != null && sameSlope.m == line.m) {
			line.c = Math.max(line.c, sameSlope.c);
			hull.remove(sameSlope);
		}
		line below = hull.lower(line), above = hull.higher(line);
		if (below != null && above != null && bad(below, line, above)) // line not on the hull
			return;

		hull.add(line);
		// remove lines above line
		while (above != null) {
			line aboveAbove = hull.higher(above);
			if (aboveAbove == null || !bad(line, above, aboveAbove))
				break;
			hull.remove(above);
			above = aboveAbove;
		}
		// remove line below line
		while (below != null) {
			line belowBelow = hull.lower(below);
			if (belowBelow == null || !bad(belowBelow, below, line))
				break;
			hull.remove(below);
			below = belowBelow;
		}
	}

	static class machine {
		int day, price, sale, profit;

		machine(Scanner sc) throws IOException {
			day = sc.nextInt();
			price = sc.nextInt();
			sale = sc.nextInt();
			profit = sc.nextInt();
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		for(int t=1;;t++) {
			int n = sc.nextInt(), C = sc.nextInt(), D = sc.nextInt();
			if(n==0) break;
			machine[] machines = new machine[n];
			for (int i = 0; i < n; i++)
				machines[i] = new machine(sc);
			Arrays.sort(machines, (x, y) -> x.day - y.day);
			hull = new TreeSet();
			line line = new line(0, C);
			insert(line);
			long ans = C;
			for (machine machine : machines) {
				int day = machine.day;
				// at the start of this day, what is the maximum money I can have ?
				long maxMoney = query(day - 1);
				if (maxMoney < machine.price)
					continue;
				long currMoney = maxMoney - machine.price + machine.sale;
				ans = Math.max(ans, currMoney + (D - day) * 1L * machine.profit);
				line newLine = new line(machine.profit, currMoney - machine.profit * 1L * day);
				insert(newLine);
			}
			out.printf("Case %d: %d\n",t,ans);
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

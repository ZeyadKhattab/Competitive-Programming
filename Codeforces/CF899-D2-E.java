import java.io.*;
import java.util.*;

public class Segments Removal {

	static TreeSet<Integer> leftBound, rightBound;
	static TreeSet<tuple>pq;
	static HashMap<Integer,tuple> mapL,mapR;
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n=sc.nextInt();
		int []a=new int [n];
		for(int i=0;i<n;i++)
			a[i]=sc.nextInt();
		leftBound=new TreeSet();
		rightBound=new TreeSet();
		pq=new TreeSet();
		mapL=new HashMap();
		mapR=new HashMap();

		for(int l=0;l<n;l++) {
			int r=l;
			for(r=l;r<n && a[r]==a[l];r++);
			r--;
			tuple tuple=new tuple(l, r, a[l], r-l+1);
			add(tuple);
			l=r;
		}
		int ans=0;
		while(!pq.isEmpty()) {
			tuple curr=pq.first();
			int l=curr.l,r=curr.r;
			delete(curr);
			ans++;
			Integer leftR=rightBound.lower(l),rightL=leftBound.higher(r);
			
			if(leftR!=null && rightL!=null) {
				tuple L =mapR.get(leftR),R=mapL.get(rightL);
				if(L.v == R.v) {
					tuple newTuple = new tuple(L.l, R.r, L.v, L.c+R.c);
					delete(L);
					delete(R);
					add(newTuple);
				}
			}
			
		}
		out.println(ans);
		out.close();

	}
	static void delete(tuple tuple) {
		int l=tuple.l,r=tuple.r;
		mapL.remove(l);
		mapR.remove(r);
		pq.remove(tuple);
		leftBound.remove(l);
		rightBound.remove(r);
	}
	static void add(tuple tuple) {
		int l=tuple.l,r=tuple.r;
		mapL.put(l,tuple);
		mapR.put(r,tuple);
		pq.add(tuple);
		leftBound.add(l);
		rightBound.add(r);
	}
	static class tuple implements Comparable<tuple>{
		int l,r,v,c;
		tuple(int l,int r,int v,int c){
			this.l=l;
			this.r=r;
			this.v=v;
			this.c=c;
		}
		@Override
		public int compareTo(tuple o) {
			if(c!=o.c)
				return o.c-c;
			if(l!=o.l)
				return l-o.l;
			if(r!=o.r)
				return r-o.r;
			return v-o.v;
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

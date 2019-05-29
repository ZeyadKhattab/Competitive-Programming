/*
 * Because 2^x +2^(x+1) + 2^ (x+2) +.. + 2^(x+i) < 2^ (x+i+1)
 * it is obvious that all shortest paths will be part of an MST
 * After constructing an MST, we just need to count for each edge the number of pairs of nodes that uses it
 *  by sub[u] * n-sub[u]
 */

import java.io.*;
import java.util.*;

public class HackerLand {

	static long []cnt;
	static int []sub;
	static ArrayList<Edge> []adj;
	static int dfs(int u,int p) {
		int ans=1;
		for(Edge nxt:adj[u]) {
			int x=0;
			if(nxt.u!=u && nxt.u!=p)
				x=dfs(nxt.u,u);
			if(nxt.v!=u && nxt.v!=p)
				x=dfs(nxt.v,u);
			ans+=x;
			cnt[nxt.c]+=x*1L*(adj.length-x);
		}
		
		return sub[u]=ans;
	}
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n=sc.nextInt(),m=sc.nextInt();
		 adj=new ArrayList[n];
		for(int i=0;i<n;i++)
			adj[i]=new ArrayList();
		Edge[]edges=new Edge[m];
		for(int i=0;i<m;i++)
			edges[i]=new Edge(sc.nextInt()-1,sc.nextInt()-1,sc.nextInt());
		DSU dsu=new DSU(n);
		Arrays.sort(edges);
		for(Edge e:edges)
			if(dsu.union(e.u, e.v))
			{
				adj[e.u].add(e);
				adj[e.v].add(e);
			}
		
		sub=new int [n];
		cnt=new long [(int)1e6];
		dfs(0,-1);
		for(int i=0;i<cnt.length;i++) {
			String binary=Long.toBinaryString(cnt[i]);
			cnt[i]=0;
			for(int bit=binary.length()-1,j=0;bit>=0;bit--,j++)
				cnt[i+j]+=binary.charAt(bit)-'0';
		}
		boolean first=false;
		for(int i=cnt.length-1;i>=0;i--)
			if(first || cnt[i]>0) {
				out.print(cnt[i]);
				first=true;
			}
		out.close();
		
	}
	static class DSU
	{
		int []p,r;
		DSU(int n){
			p=new int [n];
			r=new int [n];
			for(int i=0;i<n;i++)
				p[i]=i;
		}
		boolean union(int x,int y) {
			x=findSet(x);
			y=findSet(y);
			if(x==y)
				return false;
			if(r[x]>r[y]) {
				p[y]=x;
			}
			else
			{
				p[x]=y;
				if(r[x]==r[y])
					r[y]++;
			}
			return true;
			
		}
		int findSet(int x) {
			if(p[x]==x)
				return x;
			else
				return p[x]=findSet(p[x]);
		}
	}
	static class Edge implements Comparable<Edge>
	{
		int u,v,c;
		Edge(int a,int b,int C){
			u=a;
			v=b;
			c=C;
		}
		@Override
		public int compareTo(Edge o) {
			return c-o.c;
		}
		public String toString() {
			return u+" "+v+" "+c;
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

	}

}

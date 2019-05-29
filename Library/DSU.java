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

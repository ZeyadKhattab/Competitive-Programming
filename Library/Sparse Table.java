static class SparseTable {
		// table [i][j] = the index with the min value from a[j] to
		// a[j+(1<<i)-1];
 
		int[] a, log,table[];
 
		SparseTable(int []a) {
			this.a=a;
			int n = a.length;
			log = new int[n+1];
			for (int i = 2; i <= n; i++)
				log[i] = log[i / 2] + 1;
			table = new int[log[n]+1][n];
			for (int i = 0; i < n; i++)
				table[0][i] = i;
			for (int i = 1, len = 2; len < n; i++, len <<= 1)
				for (int j = 0; j + len <= n; j++) {
					int u = table[i - 1][j], v = table[i - 1][j + len / 2];
					table[i][j] = a[u] < a[v] ? u : v;
				}
 
		}
 
		int query(int l, int r) {
			int len = r - l + 1;
			int lg = log[len];
			int u = table[lg][l];
			int v = table[lg][r - (1 << lg) + 1];
			int bestIdx=a[u] < a[v] ? u : v;
			return bestIdx; // return a[bestIdx] for value
		}
 
	}

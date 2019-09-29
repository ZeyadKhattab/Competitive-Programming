
public class PeopleYouMayKnow {

	public static int maximalScore(String[] in, int S, int T) {
		int n = in.length;
		int[][] adj = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				adj[i][j] = in[i].charAt(j) == 'Y' ? 1 : 0;
		int ans = removeIntersection(adj, S, T);
		int cnt1 = 0, cnt2 = 0;
		for (int i = 0; i < n; i++) {
			if (adj[i][S] == 1)
				cnt1++;
			if (adj[i][T] == 1)
				cnt2++;
		}
		long[] pre = preprocess(cnt1 < cnt2 ? S : T, cnt1 < cnt2 ? T : S, adj);
		ans += solve(pre);
		return ans;
	}

	static long[] preprocess(int S, int T, int[][] adj) {
		int cnt = 0;
		for (int i = 0; i < adj.length; i++)
			if (adj[S][i] == 1)
				cnt++;
		long[] ans = new long[cnt];
		cnt = 0;
		for (int i = 0; i < adj.length; i++)
			if (adj[S][i] == 1) {
				for (int j = 0; j < adj.length; j++)
					if (adj[i][j] == 1 && adj[j][T] == 1)
						ans[cnt] |= 1L << j;
				cnt++;
			}

		return ans;
	}

	static int solve(long[] pre) {
		int ans = Integer.MAX_VALUE;

		int sz = pre.length;
		for (int msk = 0; msk < 1 << sz; msk++) {
			long curr = 0;
			for (int i = 0; i < sz; i++)
				if ((msk & 1 << i) == 0) {
					curr |= pre[i];
				}
			ans = Math.min(ans, Integer.bitCount(msk) + Long.bitCount(curr));
		}
		return ans;
	}

	static int removeIntersection(int[][] adj, int S, int T) {
		int ans = 0;
		for (int i = 0; i < adj.length; i++)
			if (adj[S][i] == 1 && adj[T][i] == 1) {
				adj[S][i] = adj[T][i] = adj[i][S] = adj[i][T] = 0;
				ans++;
			}
		return ans;
	}

}

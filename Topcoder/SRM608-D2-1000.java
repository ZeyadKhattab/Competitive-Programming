/*
 * If there exits three nodes u,x and y such that there exists a path from u to x of length d and
 * a path from u to y of length d, and a path from x to u of length c and path from y to u of length c
 * then for any path ending at u, we can create exponential paths, one path ending at u can result in 2 paths of length
 * d+c ending at u then we can repeat this process indefinitely  
 * To know whether u,x and y exist, find number of paths of a certain length, and make sure they're positive
 */
public class BigOEasy {

	static int MOD = (int) 1e9 + 7;

	public static String isBounded(String[] mat) {
		int[][][] paths = getPaths(mat);
		int n = mat.length;
		for (int u = 0; u < n; u++)
			for (int x = 0; x < n; x++)
				for (int y = 0; y < n; y++) {
					if (x == y)
						continue;
					boolean ok1 = false, ok2 = false;
					for (int len = 1; len < paths.length; len++) {
						if (paths[len][u][x] > 0 && paths[len][u][y] > 0)
							ok1 = true;
						if (paths[len][x][u] > 0 && paths[len][y][u] > 0)
							ok2 = true;
					}

					if (ok1 & ok2)
						return "Unbounded";

				}

		return "Bounded";

	}

	static int[][][] getPaths(String[] mat) {
		int n = mat.length;
		int[][] b = new int[n][n];
		int[][][] ans = new int[n + 5][n][n];
		for (int i = 0; i < n; i++) {
			ans[0][i][i] = 1;
			for (int j = 0; j < n; j++)
				b[i][j] = mat[i].charAt(j) == 'Y' ? 1 : 0;
		}
		for (int i = 1; i < ans.length; i++) {
			ans[i] = matMult(ans[i - 1], b);
		}

		return ans;
	}

	static int[][] matMult(int[][] a, int[][] b) {
		int n = a.length;
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				long curr = 0;
				for (int k = 0; k < n; k++)
					curr += a[i][k] * 1L * b[k][j] % MOD;
				ans[i][j] = (int) curr;
			}
		return ans;
	}

}

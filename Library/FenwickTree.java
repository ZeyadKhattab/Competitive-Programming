static class FenwickTree {
		int[] bit;

		FenwickTree(int n) {
			bit = new int[n + 1];
		}

		void update(int idx, int v) {
			while (idx < bit.length) {
				bit[idx] += v;
				idx += idx & -idx;
			}
		}

		int query(int idx) {
			int ans = 0;
			while (idx > 0) {
				ans += bit[idx];
				idx -= idx & -idx;
			}
			return ans;
		}

		int query(int l, int r) {
			return query(r) - query(l - 1);
		}
	}

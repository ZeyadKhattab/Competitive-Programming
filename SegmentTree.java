public class SegmentTree {
	int n, a[];
	long[] sum, lazy;

	SegmentTree(int[] a) {
		this(a.length);
		this.a = a;
		build(1, 0, n - 1);
	}

	SegmentTree(int n) {
		this.n = n;
		sum = new long[4 * n];
		lazy = new long[4 * n];
	}

	void build(int node, int tl, int tr) {
		if (tl == tr)
			sum[node] = a[tl];
		else {
			int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
			build(left, tl, mid);
			build(right, mid + 1, tr);
			merge(node);
		}
	}

	void updatePoint(int idx, int v) { // a[idx]+=v
		updatePoint(1, 0, n - 1, idx, v);
	}

	void updatePoint(int node, int tl, int tr, int idx, int v) {
		if (tl == tr)
			sum[node] += v;
		else {
			int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
			if (idx <= mid)
				updatePoint(left, tl, mid, idx, v);
			else
				updatePoint(right, mid + 1, tr, idx, v);
			merge(node);
		}
	}

	long query(int l, int r) {
		return query(1, 0, n - 1, l, r);
	}

	long query(int node, int tl, int tr, int l, int r) {
		if (tr < l || r < tl)
			return 0;
		if (tl >= l && tr <= r)
			return sum[node];
		int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
		propagate(node, tl, tr);
		return query(left, tl, mid, l, r) + query(right, mid + 1, tr, l, r);
	}

	void merge(int node) {
		sum[node] = sum[2 * node] + sum[2 * node + 1];
	}

	void updateRange(int l, int r, int v) {
		updateRange(1, 0, n - 1, l, r, v);
	}

	void updateRange(int node, int tl, int tr, int l, int r, int v) {
		if (tr < l || r < tl)
			return;
		if (tl >= l && tr <= r) {
			sum[node] += (tr - tl + 1) * 1L * v;
			lazy[node] += v;
			return;
		}
		int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
		propagate(node, tl, tr);
		updateRange(left, tl, mid, l, r, v);
		updateRange(right, mid + 1, tr, l, r, v);
		merge(node);
	}

	private void propagate(int node, int tl, int tr) {
		int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
		sum[left] += (mid - tl + 1) * 1L * lazy[node];
		lazy[left] += lazy[node];
		sum[right] += (tr - mid) * 1L * lazy[node];
		lazy[right] += lazy[node];
		lazy[node] = 0;

	}
}

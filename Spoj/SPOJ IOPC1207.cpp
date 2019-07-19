/*
 * The order of the flip operations does not matter, we just need to know for each cell how many times it was flipped
 * We will consider each dimension independently.
 * Fist, we can solve it in 2D
 * Find the number of columns that are completely flipped and also the rows (using 3 segmentTrees),the answer for any such grid is
 * all cells coloured in X Dimension + Y dimension - 2* coloured by both
 * then we will extend it to 3D with similar logic
 */
#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
typedef pair<int, int> ii;
typedef vector<int> vi;
typedef vector<ii> vii;
const int N = 100005;
#define sz(c) int((c).size())
#define all(c) (c).begin(), (c).end()
#define pb push_back
#define INF int(1e9)
#define EPS 1e-9
#define MOD int(1e9 + 7)
struct SegmentTree {
	int n;
	int sum[4 * N], lazy[4 * N];
	SegmentTree(int a) {
		n = a;
		for (int i = 0; i < 4 * N; i++)
			lazy[i] = sum[i] = 0;
	}
	void propagate(int node, int tl, int mid, int tr) {
		if (!lazy[node])
			return;
		for (int i = 2 * node; i <= 2 * node + 1; i++) {
			lazy[i] ^= 1;
			if (i == 2 * node)
				sum[i] = (mid- tl + 1) - sum[i];
			else
				sum[i] = (tr-mid) - sum[i];
		}

		lazy[node] = 0;
	}
	void updateRange(int node, int tl, int tr, int l, int r) {
		if (r < tl || tr < l)
			return;
		if (tl >= l && tr <= r) {
			lazy[node] ^= 1;
			sum[node] = (tr - tl + 1) - sum[node];
			return;
		}
		int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
		propagate(node, tl, mid, tr);
		updateRange(left, tl, mid, l, r);
		updateRange(right, mid + 1, tr, l, r);
		sum[node] = sum[left] + sum[right];
	}
	void updateRange(int l, int r) {
		updateRange(1, 0, n - 1, l, r);
	}
	int query(int node, int tl, int tr, int l, int r) {
		if (r < tl || tr < l)
			return 0;
		if (tl >= l && tr <= r)
			return sum[node];

		int mid = (tl + tr) / 2, left = node * 2, right = left + 1;
		propagate(node, tl, mid, tr);
		return query(left, tl, mid, l, r) + query(right, mid + 1, tr, l, r);
	}
	int query(int l, int r) {
		return query(1, 0, n - 1, l, r);
	}
};

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(NULL);
	int tc, q, x1, y1, z1, x2, y2, z2, t, l, r;
	int n[3];
	cin >> tc;

	while (tc--) {
		for (int i = 0; i < 3; i++)
			cin >> n[i];
		SegmentTree t0(n[0]);
		SegmentTree t1(n[1]);
		SegmentTree t2(n[2]);

		cin >> q;
		while (q--) {
			cin >> t;
			if (t == 3) {
				cin >> x1 >> y1 >> z1 >> x2 >> y2 >> z2;
				ll cols = t0.query(x1, x2);
				ll ans = cols * (y2 - y1 + 1);
				ll rows = t1.query(y1, y2);
				ans -= cols * rows;
				ans += (x2 - x1 + 1 - cols) * rows;
				ll R=y2-y1+1,C=x2-x1+1;
				ll flippedgrids = t2.query(z1, z2);
				ll grids = z2 - z1 + 1;
				ll cells = R*C;
				ans = ans * (grids - flippedgrids) + (cells - ans) * (flippedgrids);
				cout << ans << "\n";
			} else {
				cin >> l >> r;
				if (t == 0)
					t0.updateRange(l, r);
				else if (t == 1)
					t1.updateRange(l, r);
				else
					t2.updateRange(l, r);

			}

		}
	}

}

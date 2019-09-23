import java.util.Arrays;

public class BerryPacker {

	int[] first, period, cnt;
	Integer[] msks;

	public double bestPacking(int[] first, int[] period, int berries) {
		double ans = 0;
		this.first = first;
		this.period = period;
		int n = period.length;
		msks = new Integer[1 << n];
		for (int i = 0; i < 1 << n; i++)
			msks[i] = i;

		int lo = ceil(berries, 9), hi = berries;
		init(lo - 1);
		for (int boxes = lo; boxes <= hi; boxes++) {
			ans = Math.max(ans, solve(boxes, berries));
		}

		return ans / period.length;
	}

	void init(int boxes) {
		int n = period.length;

		cnt = new int[1 << n];
		for (int box = 0; box < boxes; box++) {
			int msk = 0;
			for (int i = 0; i < n; i++) {
				if (box >= first[i] && (box - first[i]) % period[i] == 0)
					msk |= 1 << i;
			}
			cnt[msk]++;

		}
	}

	double solve(int boxes, int berries) {
		int currBox = boxes - 1;
		int msk = 0;
		int n = period.length;
		double[] val = new double[1 << n];

		for (int i = 0; i < n; i++) {
			if (currBox >= first[i] && (currBox - first[i]) % period[i] == 0)
				msk |= 1 << i;
		}
		cnt[msk]++;
		for (msk = 1; msk < 1 << n; msk++) {
			for (int i = 0; i < n; i++)
				if ((msk & 1 << i) != 0) {
					int seenBoxes = seenBoxes(boxes, first[i], period[i]);
					if (seenBoxes != 0)
						val[msk] += 1.0 / seenBoxes;
				}
		}
		double ans = 0;
		Arrays.sort(msks, (x, y) -> Double.compare(val[y], val[x]));
		for (int i = 0; i < 1 << n; i++) {
			msk = msks[i];
			ans += val[msk] * cnt[msk];
		}
		berries -= boxes;
		for (int i = 0; i < 1 << n; i++) {
			msk = msks[i];

			int min = Math.min(cnt[msk] * 8, berries);
			berries -= min;
			ans += val[msk] * min;

		}
		return ans * boxes;
	}

	int ceil(int a, int b) {
		return (a + b - 1) / b;
	}

	int seenBoxes(int boxes, int a, int k) {
		if (a >= boxes)
			return 0;
		return ceil(boxes - a, k);

	}

}

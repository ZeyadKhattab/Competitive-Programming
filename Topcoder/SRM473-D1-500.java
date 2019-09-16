// Idea: https://en.wikipedia.org/wiki/Thales%27s_theorem
import java.util.*;

public class RightTriangle {

	public static long triangleCount(int places, int points, int a, int b, int c) {
		if (places % 2 == 1)
			return 0;
		TreeSet<Integer> free = new TreeSet();
		for (int i = 0; i < places; i++)
			free.add(i);

		HashSet<Integer> red = new HashSet();
		for (int n = 0; n < points; n++) {
			int p = (int) ((a * 1L * n * n + b * 1L * n + c) % places);
			Integer cand = free.ceiling(p);
			if (cand != null) {
				red.add(cand);
				free.remove(cand);
				continue;
			}
			cand = free.ceiling(0);
			red.add(cand);
			free.remove(cand);

		}
		long ans = 0;
		for (int x : red) {
			if (x < places / 2 && red.contains(x + places / 2))
				ans += points - 2;
		}
		return ans;

	}

}

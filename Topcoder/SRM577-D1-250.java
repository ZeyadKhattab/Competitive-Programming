import java.util.*;

public class EllysRoomAssignmentsDiv1  {

	public static double getAverage (String[] ratings) {
		ArrayList<Integer> a = new ArrayList();
		StringBuilder sb = new StringBuilder();
		for (String s : ratings)
			sb.append(s);
		String[] x = sb.toString().split(" ");

		for (String y : x)
			a.add(Integer.parseInt(y));

		double ans = 0;
		int myScore = a.get(0);
		Collections.sort(a, Collections.reverseOrder());
		int R = (a.size() + 19) / 20;
		int roomSize = a.size() / R;
		int maxRoomSize = roomSize * R == a.size() ? roomSize : roomSize + 1;

		for (int i = 0; i < a.size(); i += R) {
			boolean in = false;
			double sum = 0;
			for (int j = i; j < Math.min(a.size(), i + R); j++) {
				if (a.get(j) == myScore)
					in = true;
				sum += a.get(j);
			}
			if (i + R >= a.size()) {
				if (in) {
					ans /= R;
					ans += myScore;
					ans /= maxRoomSize;
					break;
				} else {
					double add = (a.size() - i) * 1.0 / R;
					sum = sum * 1.0 / (a.size() - i);
					double tmp = ans / R + sum + myScore;
					tmp /= maxRoomSize;
					double tmp2 = ans / R + myScore;
					tmp2 /= roomSize;
					ans = tmp * add + tmp2 * (1 - add);
				}

			} else if (!in)
				ans += sum;

		}

		return ans;

	}

	
}

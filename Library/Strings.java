import java.io.*;
import java.util.*;

public class Strings {

	static int[] prefixFunction(char[] s) {
		int n = s.length, pi[] = new int[n];
		for (int i = 1, j = 0; i < n; ++i) {
			while (j > 0 && s[i] != s[j])
				j = pi[j - 1];
			if (s[i] == s[j])
				++j;
			pi[i] = j;
		}
		return pi;
	}

	static int period(int[] pi) {
		int n = pi.length;
		int k = n - pi[n - 1];
		return n % k == 0 ? k : n;
	}

	static ArrayList<Integer> searchOccurences(String s, String t) {
		String concat = s + "#" + t;
		int[] pi = prefixFunction(concat.toCharArray());
		ArrayList<Integer> ans = new ArrayList();
		int m = t.length(), n = s.length();
		for (int i = n + 1; i < concat.length(); i++)
			if (pi[i] == n)
				ans.add(i - 2 * n);
		return ans;
	}

	static int[] cntPrefixes(String s) { // ans[i] = cnt of prefix of length i (ignoring ans [0])
		int[] pi = prefixFunction(s.toCharArray());
		int n = s.length();
		int[] ans = new int[n + 1];
		for (int x : pi)
			ans[x]++;
		for (int i = n - 1; i > 0; i--)
			ans[pi[i - 1]] += ans[i];
		for (int i = 0; i <= n; i++)
			ans[i]++;
		return ans;
	}

	static int[] cntPrefixes(String s, String t) {
		String concat = s + "#" + t;
		int[] pi = prefixFunction(concat.toCharArray());
		int n = s.length();
		int[] ans = new int[n + 1];
		for (int i = n + 1; i < concat.length(); i++)
			ans[pi[i]]++;
		for (int i = n - 1; i > 0; i--)
			ans[pi[i - 1]] += ans[i];
		return ans;
	}

	static long numSubStrings(String s) {
		StringBuilder sb = new StringBuilder();
		long ans = 0;
		for (int i = 0; i < s.length(); i++) {
			sb.append(s.charAt(i));
			int[] pi = prefixFunction(sb.reverse().toString().toCharArray());
			int piMax = 0;
			for (int x : pi)
				piMax = Math.max(piMax, x);
			ans += i + 1 - piMax;
		}
		return ans;
	}

}

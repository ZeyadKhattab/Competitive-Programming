import java.io.*;
import java.util.*;

public class Strings {

	static int[] prefixFunction(char[] s) 
	{
		int n = s.length, pi[] = new int[n];
		for (int i = 1, j = 0; i < n; ++i) 
		{
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

}

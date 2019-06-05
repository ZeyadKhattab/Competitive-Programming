import java.util.*;

public class Sieve {

	static ArrayList<Integer> primes;
	static boolean[] isPrime;

	static void sieve(int N) {
		primes = new ArrayList();
		isPrime = new boolean[N + 5];
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;
		for (int i = 2; i < isPrime.length; i++)
			if (isPrime[i]) {
				primes.add(i);
				for (int j = i + i; j < isPrime.length; j += i)
					isPrime[j] = false;
			}
	}

}

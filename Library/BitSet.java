static class BitSet implements Comparable<BitSet> {
		long[] msks;

		BitSet(int n) {
			msks = new long[(n + 63 - 1) / 63];
		}

		long hash() {
			long h = 0;
			for (long x : msks)
				h = h * 239017239 + x;
			return h;
		}

		void set(int idx) {
			int bit = idx % 63;
			msks[idx / 63] |= 1L << bit;
		}

		void or(BitSet other) {
			for (int i = 0; i < msks.length; i++)
				msks[i] |= other.msks[i];
		}

		void xor(BitSet other) {
			for (int i = 0; i < msks.length; i++)
				msks[i] ^= other.msks[i];
		}

		static BitSet xor(BitSet a, BitSet b, int n) {
			BitSet ans = new BitSet(n);
			for (int i = 0; i < a.msks.length; i++)
				ans.msks[i] = a.msks[i] ^ b.msks[i];
			return ans;
		}

		@Override
		public int compareTo(BitSet o) {
			for (int i = 0; i < msks.length; i++)
				if (msks[i] != o.msks[i])
					return Long.compare(msks[i], o.msks[i]);
			return 0;
		}
	}

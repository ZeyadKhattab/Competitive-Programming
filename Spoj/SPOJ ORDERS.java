import java.io.*;
import java.util.*;

public class ORDERS  {

	static int MAXSZ = 477;
	static int nxtId;

	static Block insert(int needed, ArrayList<Block> blocks, int value) {

		int sum = 0;
		Block block = blocks.get(0);

		Block last = blocks.get(blocks.size() - 1);

		while (true) {
			int sz = block.size;
			if (sum + sz - 1 >= needed) {
				return block.insert(needed - sum, value);
			}
			sum += sz;

			if (block.nxt == -1) {
				last = block;
				break;
			}
			block = blocks.get(block.nxt);

		}

		return last.insert(last.size, value);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			ArrayList<Block> blocks = new ArrayList();
			nxtId = 0;
			for (int pos = 0; pos < n; pos++) {

				int needed = pos - sc.nextInt();
				if (pos == 0) {
					blocks.add(new Block(-1));
					blocks.get(0).insert(0, 0);
					continue;
				}

				Block x = insert(needed, blocks, pos);
				if (x != null)
					blocks.add(x);

			}
			Block block = blocks.get(0);
			int[] pos = new int[n];
			int curr = 1;
			while (true) {

				for (int x : block.block)
					pos[x] = curr++;

				if (block.nxt == -1)
					break;

				block = blocks.get(block.nxt);

			}
			for (int i = 0; i < n; i++) {
				if (i > 0)
					out.print(" ");
				out.print(pos[i]);

			}
			out.println();

		}

		out.close();

	}

	static class Block {
		ArrayList<Integer> block;
		int id, nxt, size;

		Block(int nxt) {
			this.id = nxtId++;
			this.nxt = nxt;
			block = new ArrayList();
		}

		Block(int nxt, ArrayList<Integer> block) {
			this.id = nxtId++;
			this.nxt = nxt;
			this.block = block;
			size = block.size();
		}

		public Block insert(int pos, int value) {
			ArrayList<Integer> newBlock = new ArrayList();
			for (int i = 0; i < pos; i++)
				newBlock.add(block.get(i));
			newBlock.add(value);
			for (int i = pos; i < block.size(); i++)
				newBlock.add(block.get(i));
			size++;
			block = newBlock;
			if (size > MAXSZ) {
				return adjust();
			}
			return null;
		}

		Block adjust() {
			ArrayList<Integer> move = new ArrayList();
			for (int i = MAXSZ / 2; i < size; i++)
				move.add(block.get(i));
			while (size > MAXSZ / 2) {
				block.remove(--size);

			}
			Block newBlock = new Block(nxt, move);
			nxt = newBlock.id;
			return newBlock;
		}
	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		Scanner(String fileName) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(fileName));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}

	}

}

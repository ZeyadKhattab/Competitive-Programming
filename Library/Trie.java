	static class Trie {
		node root;

		Trie() {
			root = new node();
		}

		void insert(char[] x) {
			root.insert(x, 0);
		}

	}

	static class node {
		node[] nodes;
		int edges;
		boolean end;

		node() {
			nodes = new node[26];
		}

		void insert(char[] x, int idx) {
			if (idx == x.length) {
				end = true;
				return;
			}
			int curr = x[idx] - 'a';
			if (nodes[curr] == null) {
				nodes[curr] = new node();
				edges++;
			}
			nodes[curr].insert(x, idx + 1);
		}
	}

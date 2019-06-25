// We will add an edge between player a and player b if a is better than b
// and any valid topological sorting of the graph will be a correct answer
#include <bits/stdc++.h>

using namespace std;
const int N = 26000;
vector<int> adj[N];
int deg[N];

void addEdge(int u, int v) {

    u--;
    v--;
    adj[u].push_back(v);
    deg[v]++;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    int n, m;
    cin >> n;
    cin >> m;
    for (int i = 0; i < n; i++)
        deg[i] = 0;
    while (m--) {
        int u, v;
        string line;
        cin >> line;
        string x = line.substr(0, line.find(">"));
        if (x.find(",") < 10) {

            v = stoi(line.substr(line.find(">") + 1, line.length()));
            u = stoi(x.substr(0, x.find(",")));
            addEdge(u, v);
            u = stoi(x.substr(x.find(",") + 1));
            addEdge(u, v);
        } else {
            u = stoi(line.substr(0, line.find(">")));
            v = stoi(line.substr(line.find(">") + 1, line.find(",")));

            addEdge(u, v);
            v = stoi(line.substr(line.find(",") + 1));
            addEdge(u, v);

        }
    }
    queue<int> queue;
    vector<int> ans;
    for (int i = 0; i < n; i++)
        if (deg[i] == 0)
            queue.push(i);
    while (!queue.empty()) {
        int curr = queue.front();
        queue.pop();
        ans.push_back(curr);
        for (int v : adj[curr])
            if (--deg[v] == 0)
                queue.push(v);
    }
    bool first = true;
    if (ans.size() != n)
        cout << 0;

    else {

        for (int x:ans) {
            if (first)
                first = false;
            else
                cout << " ";
            cout << x + 1;
        }


    }
    cout << "\n";


}




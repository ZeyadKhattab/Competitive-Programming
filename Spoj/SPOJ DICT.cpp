#include <bits/stdc++.h>

using namespace std;
const int N=26000;
string s[N];
vector<int> ans;
struct Trie {
        int stringIdx;
        Trie* nodes[26];
        Trie() {
            memset(nodes, 0, sizeof(nodes));
            stringIdx=-1;
        }


        void insert(string x,int idx,int sIdx) {
            if(idx==x.length()){
                stringIdx = sIdx;
               
            }
            else {
                int cur = x[idx] - 'a';
                if(nodes[cur] == 0 )
                    nodes[cur] = new Trie();
                nodes[cur]->insert(x,idx+1,sIdx);
            }
        }




        void query(string x,int idx,bool first) {
            if (idx>=x.length()) {
                if (!first && stringIdx != -1)
                    ans.push_back(stringIdx);
                for (int i = 0; i < 26; i++)
                    if (nodes[i] != 0)
                        nodes[i]->query(x,idx+1,false);
                return;
            }
          int curr = x[idx] - 'a';
            if (nodes[curr] != 0)
                nodes[curr]->query(x,idx+1,true);
        }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    int n,k;
    cin>>n;
    Trie root;
     for (int i = 0; i < n; i++) {
        cin>>s[i];
        root.insert(s[i],0,i);
     }
    cin>>k;
    for (int t = 1; t <= k; t++) {

        cout<<"Case #"<<t<<":\n";
        string x;
        cin>>x;
        root.query(x,0,true);
        for (int idx : ans) 
            cout<<s[idx]<<"\n";
         if (ans.size() == 0)
             cout<<"No match."<<"\n";
        ans.clear();
            
    }       

    
}

    
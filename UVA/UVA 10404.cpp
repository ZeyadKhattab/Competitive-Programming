// dp(n) = 1 if the players starting with n stones can win
#include <bits/stdc++.h>

using namespace std;
const int N=2000000;
const int M=15;
int dp[N];

int options[M];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(NULL);
    int n,m;
    dp[0]=0;
    while(cin>>n>>m) {
        for(int i=0;i<m;i++)
            cin>>options[i];
        for(int stones=1;stones<=n;stones++){
            int ans=0;
            for(int i=0;i<m;i++) {
                int x=options[i];
                if(x<=stones) {
                    if(!dp[stones-x])
                        ans=1;
                }
            }
            dp[stones]=ans;
        }

        cout<<(dp[n]?"Stan wins":"Ollie wins")<<"\n";
    }


}

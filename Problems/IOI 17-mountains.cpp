/* 
* Let dp(l,r) = the maximum answer for the points between l and r
* for each dp(l,r) we can leave the rth point empty dp(l,r) = dp(l,r-1)
* or we can use the rth cell, let us loop over l < r while keeping track of the leftmost point 
* that is impossible to be paired with r (last),
* if it is possible to pair l and r together, then let last=l and add dp(l+1,last-1) to a running sum
* otherwise, consider adding dp(l,last-1) to dp(l,r) 
* If we reach some point l that is impossible to pair with r, then for x<l we can disregard all points >=l because if it can be paired
* with r, then this point l will above the line joining x and all i>l && i<=r
*/


#include "mountains.h"
#define x first
#define y second
using namespace std;
typedef pair<long long,long long> point;
const int MAX = 2005;
int dp[MAX][MAX];
point p[MAX];
long long ccw(point a, point b, point c)
{
	return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
}
int maximum_deevs(vector<int> y)
{
	int n = y.size();
	for (int i = 0; i < n; i++)
	{
		p[i].y = y[i];
		p[i].x = i;
	}
	for (int r = 0; r < n; r++)
	{
		int sum = 0, last = r;
		
		dp[r][r] = 1;
		for (int l = r - 1; l >= 0; l--)
		{
			dp[l][r] = dp[l][r - 1];
			if (ccw(p[l], p[last], p[r]) >= 0)
			{
				sum += dp[l + 1][last-1];
				last = l;
			}
			dp[l][r] = max(dp[l][r], 1 + sum + dp[l][last-1]);
		}
	}
	return dp[0][n-1];
}

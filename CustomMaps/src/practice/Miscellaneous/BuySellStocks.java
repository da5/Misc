package practice.Miscellaneous;

public class BuySellStocks {

    /*
        https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
        Input: [7,1,5,3,6,4]
        Output: 5
        Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
                     Not 7-1 = 6, as selling price needs to be larger than buying price.

        Input: [7,6,4,3,1]
        Output: 0
        Explanation: In this case, no transaction is done, i.e. max profit = 0.
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[] minPrice = new int[n];
        minPrice[0] = prices[0];
        for (int i = 1; i < n; i++) {
            minPrice[i] = Math.min(minPrice[i - 1], prices[i]);
        }

        int max = 0;
        for (int i = 1; i < n; i++) {
            max = Math.max(max, prices[i] - minPrice[i]);
        }
        return max;
    }

    /*
        https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
        Input: [7,1,5,3,6,4]
        Output: 7
        Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
                     Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
        Input: [1,2,3,4,5]
        Output: 4
        Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
                 Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
                 engaging multiple transactions at the same time. You must sell before buying again.

        Input: [7,6,4,3,1]
        Output: 0
        Explanation: In this case, no transaction is done, i.e. max profit = 0.
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int profit = 0;
        for (int i = 1; i < n; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    public int maxProfit2_(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int cash = 0;
        int hold = -prices[0];
        for (int i = 1; i < n; i++) {
            cash = Math.max(cash, hold + prices[i]);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }

    /*
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
     */
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int cash = 0;
        if (n > 1) {
            int hold = -prices[0];
            for (int i = 1; i < n; i++) {
                cash = Math.max(cash, hold + prices[i] - fee);
                hold = Math.max(hold, cash - prices[i]);
            }
        }
        return cash;
    }

    /*
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
     */
    public int maxProfit3(int[] prices) { //todo: debug wrong answer for {6,1,3,2,4,7}
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[] cash = new int[n];
        int[] hold = new int[n];
        hold[0] = -prices[0];
        for (int i = 1; i < n; i++) {
            cash[i] = Math.max(cash[i - 1], hold[i - 1] + prices[i]);
            hold[i] = Math.max(hold[i - 1], (i - 2 >= 0) ? cash[i - 2] : 0 - prices[i]);
        }
        return cash[n - 1];
    }

    public int maxProfit3_(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int c0 = 0, c1 = 0, c2 = 0;
        int h0 = 0, h1 = -prices[0];
        for (int i = 1; i < n; i++) {
            c0 = Math.max(c1, h1 + prices[i]);
            h0 = Math.max(h1, c2 - prices[i]);
            h1 = h0;
            c2 = c1;
            c1 = c0;
        }
        return c1;
    }

    /*
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
     */

    public int maxProfit4(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        if(k>n) {
            return maxProfit2(prices);
        }
        int[][] cash = new int[n][k+1];
        int[][] hold = new int[n][k+1];
        hold[0][0] = -prices[0];
        for(int i=1; i<n; i++) {
            hold[i][0] = Math.max(hold[i-1][0], -prices[i]);
        }

        for(int i=1; i<=k; i++) {
            hold[0][i] = -prices[0];
        }

        for(int i=1; i<n; i++) {
            for(int j=1; j<=k; j++) {
                cash[i][j] = Math.max(cash[i-1][j], hold[i-1][j-1]+prices[i]);
                hold[i][j] = Math.max(hold[i-1][j], cash[i-1][j]-prices[i]);
            }
        }

        return cash[n-1][k];

    }
}

class BuySellStocksDriver {
    public static void main(String[] args) {
        BuySellStocks buySellStocks = new BuySellStocks();
        int[] prices = {6,1,3,2,4,7};
//        System.out.println(buySellStocks.maxProfit(prices));
//        System.out.println(buySellStocks.maxProfit2_(prices));
//
//        System.out.println(buySellStocks.maxProfit(prices, 2));

        System.out.println(buySellStocks.maxProfit3_(prices));

        int[] prices4 = {3,2,6,5,0,3};
        System.out.println(buySellStocks.maxProfit4(2,prices4));



    }
}

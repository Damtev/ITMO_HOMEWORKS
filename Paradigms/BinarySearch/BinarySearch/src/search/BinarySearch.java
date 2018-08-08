package search;

public class BinarySearch {

    // PRE: a != null && for i = 0...a.length - 2  a[i] >= a[i + 1] &&  l <= min(i: a[i] <= x) <= r && immutable
    // POST: (a[r] <= x && for i = 0...r - 1    a[i] > x) || (r == a.length && for i = 0...a.length - 1   a[i] >x)
    static int itBinSearch(int[] a, int x) {
        int l =  -1;
        int r = a.length;
        int m;
        // inv: l < r - 1 && l >= 0 && r >= 0 && l <= m < r && a[l] > x && a[r] <= x
        while (l != r - 1) {
            m = (l + r) / 2;
            // a[m] > x
            if (a[m] > x) {
                // m == (l + r) / 2;
                l = m;
                // l' == (l + r) / 2
            // a[m] <= x && l < r
            } else {
                // m == (l + r) / 2;
                r = m;
                // r' == (l + r) / 2
            // a[m] <= x && l == r
            }
        }
        // l == r - 1 && r >= 0 && r <= a.length && a[l] > x && a[r] <= x
        return r;
    }

    // PRE: a != null && for i = 0...a.length - 2  a[i] >= a[i + 1] && l >= -1 && r >= 0 && l   < a.length &&
    // && r <= a.length && a[l'] >= x && a[r'] <= x && && l <= ans <= r && immutable
    // POST: (a[r] <= x && for i = 0...r - 1    a[i] > x) || (r == a.length && for i = 0...a.length - 1   a[i] >x)
    static int recBinSearch(int[] a, int x, int l, int r) {
        int m = (l + r) / 2;
        // l  == r - 1
        if (l == r - 1) {
            // l = r - 1 && a[l] > x && a[r] <= x
            return r;
        // a[m] > x && l != r - 1
        } else if (a[m] > x){
            // l - r > 1 && a[l] > x && a[r] <= x
            return recBinSearch(a, x, m, r);
            // l' == m && r' == r
            // a[m] >= x && l != r - 1
        } else {
            // l - r > 1 && a[l] > x && a[r] <= x
            return recBinSearch(a, x, l, m);
            // l' == l && r' == m
        }
    }

    public static void main(String[] args) {
        try {
            int x = Integer.parseInt(args[0]);
            int[] a = new int[args.length - 1];
            for (int i = 0; i < args.length - 1; i++) {
                a[i] = Integer.parseInt(args[i + 1]);
            }
//          System.out.println(itBinSearch(a, x));
            System.out.println(recBinSearch(a, x, -1, a.length));
        } catch (NumberFormatException error) {
            System.out.println("Invalid digital input");
        } catch (ArrayIndexOutOfBoundsException error) {
            System.out.println("Enter search number and array");
        }
    }
}

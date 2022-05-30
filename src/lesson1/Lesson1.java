package lesson1;

public class Lesson1 {
    public static void main(String[] args) {
        System.out.println(pow2(2, 0));
        System.out.println(pow2(3, 3));
        System.out.println(pow2(2, 4));
        System.out.println(sum(1, 5)); // 1+2+3+4+5
        System.out.println(sum(1, 100));
    }

    static int pow2(int a, int n) {
        int res = 1;
        int a2 = a * a;
        int n2 = n;
        if (n == 0) return res;
        if (n % 2 != 0) {
            n2 = n - 1;
        }
        for (int i = 0; i < n2; i += 2) {
            res = res * a2;
        }
        if (n != n2) {
            res = res * a;
        }
        return res;
    }

    static int sum(int min, int max) {
        int res = min;
        for (int i = min + 1; i <= max; i++) {
            res += i;
        }
        return res;
    }
}

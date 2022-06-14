package lesson5;

import java.math.BigDecimal;

public class Main {


    public static void main(String[] args) {
        System.out.println(" pow (10,3) " + pow(new BigDecimal(10), 3));
        jump(1, 1, 1);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.printf("%3d", board[i][j]);
            }
            System.out.println();
        }
    }

    static BigDecimal pow(BigDecimal num, int n) {
        if (n < 0) {
            throw new ArithmeticException(" n<0 ");
        }
        if (n == 0) {
            return new BigDecimal(1);
        }
        return pow(num, n - 1).multiply(num);
    }

    private static final int BOARD_SIZE = 8;
    private static final int[] dx = new int[]{2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] dy = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
    static int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    static char[] l = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private static int jump(int x, int y, int step) {
        board[x][y] = step;
        int i;
        for (i = 0; i < BOARD_SIZE; i++) {
            int x1 = x + dx[i];
            int y1 = y + dy[i];

            if ((x1 >= 0) && (y1 >= 0) && (x1 < BOARD_SIZE) && (y1 < BOARD_SIZE) && (board[x1][y1] == 0)) {
                System.out.printf("%2s:  %s%d  ->   %s%d%n", step, l[x], y + 1, l[x1], y1 + 1);
                step = jump(x1, y1, step + 1);
            }
        }
        return step;
    }
}

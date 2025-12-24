package utils;

public final class Math {
    public static double intPow(double number, int index) {
        double result = 1;
        if (index == 0) return 1;
        else if (index < 0) {
            for (int i = 0; i < -index; i++) {
                result *= number;
            }
            return 1 / result;
        } else {
            for (int i = 0; i < index; i++) {
                result *= number;
            }
            return result;
        }
    }

    public static int abs(int x) {
        if (x < 0) return -x;
        else return x;
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i = i + 1) {
            result *= i;
        }
        return result;
    }

    public static boolean isPrime(int n) {
        if (n <= 1)
            return false;

        for (int i = 2; i <= n / 2; i++)
            if (n % i == 0)
                return false;

        return true;
    }

    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }
        int sum = 0;
        for (int i = 1; i < number; i++) {
            if (number % i == 0) {
                sum += i;
            }
        }
        return sum == number;
    }

    public static int findGCD(int x, int y) {
        int gcd = 1;
        for (int i = 1; i <= x && i <= y; i++) {
            if (x % i == 0 && y % i == 0)
                gcd = i;
        }
        return gcd;
    }
}

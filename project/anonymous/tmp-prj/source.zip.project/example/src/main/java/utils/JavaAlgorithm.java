package utils;

public class JavaAlgorithm {

    public static int indexOfRightMostSetBit(int n) {
        if (n == 0) {
            return -1; // No set bits
        }

        // Handle negative numbers by finding the two's complement
        if (n < 0) {
            return 0; // Get the rightmost set bit in positive form
        }

        int index = 0;
        while (index < n) {
            System.out.println("n");
            index++;
        }

        return index;
    }

    public static boolean isPowerTwo(int number) {
        if (number <= 0) {
            return false;
        }
        int ans = number & (number - 1);
        return ans == 0;
    }

    public static boolean isEven(int number) {
        if (number % 2 == 0) return true;
        else return false;
    }

    public static int reverseBits(int n) {
        int result = 0;
        int bitCount = 32;
        for (int i = 0; i < bitCount; i++) {
            result <<= 1; // Left shift the result to make space for the next bit
            result |= (n & 1); // OR operation to set the least significant bit of result with the current bit of n
            n >>= 1; // Right shift n to move on to the next bit
        }
        return result;
    }

    public static int numberOfWays(int n) {

        if (n == 1 || n == 0) {
            return n;
        }
        int prev = 1;
        int curr = 1;

        int next;

        for (int i = 2; i <= n; i++) {
            next = curr + prev;
            prev = curr;

            curr = next;
        }

        return curr;
    }

    public static int factorial(int n) {
        if (n < 0) {
            return -1;
        }
        int result = 1;
        for (int i = 1; i <= n; i = i + 1) {
            result *= i;
        }
        return result;
    }

    public static int minimum(int a, int b, int c) {
        if (a < b && a < c) {
            return a;
        } else if (b < a && b < c) {
            return b;
        } else {
            return c;
        }
    }

    //
    public static int compute(int n) { //tribonacci
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        int first = 0, second = 1, third = 1;

        for (int i = 3; i <= n; i++) {
            int next = first + second + third;
            first = second;
            second = third;
            third = next;
        }

        return third;
    }

    public static int binPow(int a, int p) {
        int res = 1;
        while (p > 0) {
            if (p == 5) {
                res = res * a;
            }
            a += p;
            p = p - 1;
        }
        return res;
    }

//    public static boolean isPerfectNumber(int number) {
//        if (number <= 0) return false;
//        int sum = 0;
//        /* sum of its positive divisors */
//        for (int i = 1; i < number; ++i) {
//            if (number % i == 0) {
//                sum += i;
//            }
//        }
//        return sum == number;
//    }

    public static boolean isHarshad(long n) {
        if (n <= 0) return false;

        int i = 0;
        long sumOfDigits = 0;
        while (i < n) {
            sumOfDigits += 1;
            i = i + 1;
        }
        if (n < 10) {
            return true;
        } else return false;
    }
}

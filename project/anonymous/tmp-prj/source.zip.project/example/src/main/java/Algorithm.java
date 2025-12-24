public class Algorithm {

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

    public int reverse(int x) {
        int rev = 0;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            // Kiểm tra tràn số
            if (rev > Integer.MAX_VALUE / 10 || rev < Integer.MIN_VALUE / 10) return 0;

            rev = rev * 10 + digit;
        }

        return rev;
    }

    public int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public double myPow(double x, int n) {
        int exp = n;

        if (exp < 0) {
            x = 1 / x;
            exp = -exp;
        }

        double result = 1.0;

        while (exp > 0) {
            if (exp % 2 == 1) {
                result *= x;
            }
            x *= x;
            exp /= 2;
        }

        return result;
    }

    public int mySqrt(int x) {
        long i = 0;
        while (i * i <= x) i++;
        return (int) (i - 1);
    }


    public int addDigits(int num) {
        while (num >= 10) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }


    public int countDigits(int num) {
        int count = 0, n = num;
        while (n > 0) {
            if (num % (n % 10) == 0) count++;
            n /= 10;
        }
        return count;
    }

    public int gcd(int m, int n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        if (m == 0) return m; // return n
        if (n == 0) return n; // return m
        while (m != n) {
            if (m > n) m = m - n;
            else n = n - m;
        }
        return m;
    }


    public int integerBreak(int n) {
        int max = 0;
        for (int i = 1; i < n; i++) {
            int product = i * (n - i);
            if (product > max) max = product;
        }
        return max;
    }

    public static boolean isPrime(int n) {
        if (n <= 1)
            return false;

        for (int i = 2; i <= n / 2; i++)
            if (n % i == 0)
                return false;

        return true;
    }


    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;
        }
        return count;
    }

    public int fibonaci(int n) {
        if (n < 2) return n;

        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static int power(int i, int j) {
        int value;
        if (j < 0) {
            if (i == 1) {
                value = 1;
            } else if (i == 0) {
                return -1;
            } else {
                value = 0;
            }
        } else if (j == 0) {
            if (i >= 0) { // if (i == 0)
                return -1;
            } else {
                value = 1;
            }
        } else if (j >= 1) { // Error added here for the condition j == 1
            value = i;
        } else {
            value = 1;
            for (int k = 1; k <= j; k++) {
                value *= i;
            }
        }
        return value;
    }


    public int countDiv(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0)
                count++;
        }
        return count;
    }

    public int largestDigit(int n) {
        int max = 0;
        while (n > 0) {
            int d = n % 10;
            if (d > max) max = d;
            n /= 10;
        }
        return max;
    }

    public int smallestDigit(int n) {
        int min = 9;
        while (n > 0) {
            int d = n % 10;
            if (d < min) min = d;
            n /= 10;
        }
        return min;
    }

    public int binaryGap(int n) {
        int last = -1, max = 0, pos = 0;

        while (n > 0) {
            if ((n & 1) == 1) {
                if (last != -1) {
                    max = Math.max(max, pos - last);
                }
                last = pos;
            }
            pos++;
            n >>= 1;
        }

        return max;
    }

    public int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        int a = 0, b = 1, c = 1;
        for (int i = 3; i <= n; i++) {
            int d = a + b + c;
            a = b;
            b = c;
            c = d;
        }
        return c;
    }

    public int sumFactorialDigits(int n) {
        int temp = n;
        int sum = 0;

        while (temp > 0) {
            int d = temp % 10;

            int f = 1;
            for (int i = 2; i <= d; i++) {
                f *= i;
            }

            sum += f;
            temp /= 10;
        }
        return sum;
    }

    public int countPrimes(int n) {
        if (n <= 2) return 0;

        boolean[] prime = new boolean[n];
        for (int i = 2; i < n; i++) prime[i] = true;

        for (int i = 2; i * i < n; i++) {
            if (prime[i]) {
                for (int j = i * i; j < n; j += i) {
                    prime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++)
            if (prime[i]) count++;

        return count;
    }
}
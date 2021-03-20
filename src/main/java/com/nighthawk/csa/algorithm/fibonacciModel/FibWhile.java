package com.nighthawk.csa.algorithm.fibonacciModel;

public class FibWhile extends _Fibonacci {
    public FibWhile(int nth) {
        super(nth);
    }

    @Override
    protected void init() {
        super.name = "While";
        super.source = "https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fnighthawkcoders%2Fspring-idea%2Fblob%2Fmaster%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Flessons%2Falgos%2Ffibonacci%2FFibWhile.java&style=github&showBorder=on&showLineNumbers=on&showFileMeta=on";
        long limit = super.size;
        long[] f = new long[]{0, 1};
        while (limit-- > 0) {
            super.setData(f[0]);
            f = new long[]{f[1], f[0] + f[1]};
        }
    }

    public static void main(String[] args) {
        int num = 20;   //number of Fibs, 92 is max for long
        _Fibonacci fibonacci = new FibWhile(num);
        fibonacci.print();
    }
}

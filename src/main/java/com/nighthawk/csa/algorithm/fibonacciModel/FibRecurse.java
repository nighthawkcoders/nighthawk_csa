package com.nighthawk.csa.algorithm.fibonacciModel;

public class FibRecurse extends _Fibonacci {

    public FibRecurse(int nth) {
        super(nth);
    }

    @Override
    protected void init() {
        //setup for recursion
        super.name = "Recursion";
        super.source = "https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fnighthawkcoders%2Fspring-idea%2Fblob%2Fmaster%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Flessons%2Falgos%2Ffibonacci%2FFibRecurse.java&style=github&showBorder=on&showLineNumbers=on&showFileMeta=on";
        long limit = super.size;
        long[] f = new long[]{0, 1};
        initRecurse(limit,f);
    }

    private void initRecurse(long limit, long[] f) {
        if (limit == 0) return;                                 //exit condition
        super.setData(f[0]);
        initRecurse(--limit, new long[]{f[1], f[0] + f[1]});    //recursion requires pre-increment
    }

    public static void main(String[] args) {
        int num = 20;   //number of Fibs, 92 is max for long
        _Fibonacci fibonacci = new FibRecurse(num);
        fibonacci.print();
    }
}

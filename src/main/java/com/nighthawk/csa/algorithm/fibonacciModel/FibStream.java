package com.nighthawk.csa.algorithm.fibonacciModel;

import java.util.stream.Stream;

public class FibStream extends _Fibonacci {
    public FibStream(int nth) {
        super(nth);
    }

    @Override
    protected void init() {
        super.name = "Stream";
        super.source = "https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fnighthawkcoders%2Fspring-idea%2Fblob%2Fmaster%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Flessons%2Falgos%2Ffibonacci%2FFibStream.java&style=github&showBorder=on&showLineNumbers=on&showFileMeta=on";
        Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
            .limit(super.size)
            .forEach(f -> super.setData(f[0]) );
    }

    public static void main(String[] args) {
        int num = 20;   //number of Fibs, 92 is max for long
        _Fibonacci fibonacci = new FibStream(num);
        fibonacci.print();
    }
}

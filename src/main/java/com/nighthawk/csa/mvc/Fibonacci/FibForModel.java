package com.nighthawk.csa.mvc.Fibonacci;

public class FibForModel extends FibAbstractModel {
    // zero and one argument constructors, both defer to super
    public FibForModel() { super(); }
    public FibForModel(int nth) {
        super(nth);
    }

    /*
    Abstract Polymorphic "init()" method using For
     */
    @Override
    protected void init() {
        super.name = "For";
        long limit = super.size;
        // for loops are likely the most common iteration structure, all the looping facts are in one line
        for (long[] f = new long[]{0, 1}; limit-- > 0; f = new long[]{f[1], f[0] + f[1]})
            super.setData(f[0]);
    }

    /*
    Class method "main" with purpose of testing FibFor
     */
    public static void main(String[] args) {
        FibAbstractModel fibonacci = new FibForModel(); // "FibFor" is used as initializer for the "_Fibonacci fibonacci"  object
        fibonacci.print();
    }
}

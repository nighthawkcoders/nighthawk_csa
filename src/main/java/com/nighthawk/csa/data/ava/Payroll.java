package com.nighthawk.csa.data.ava;

public class Payroll {
    private int[] itemsSold;
    private double[] wages;

    public Payroll(){
        itemsSold = new int[] {48,50,37,62,38,70,55,37,64,60};
        wages = new double[10];
    }

    public double computeBonusThreshold(){
        int highest = itemsSold[0];
        int lowest = itemsSold[0];
        int sum = itemsSold[0];

        // loops over items to find sum of items sold
        for (int i = 1; i < itemsSold.length; i++) {
            sum += itemsSold[i];
            if (itemsSold[i] > highest)
                highest = itemsSold[i];

            if (itemsSold[i] < lowest)
                lowest = itemsSold[i];
        }
        // max and min subtracted from max and min from itemsSold and divide by number of items to find bonus threshold
        return(sum - lowest - highest) / (double) (itemsSold.length - 2);

    }

    public void computeWages(double fixedWage, double perItemWage) {
        double bonusThreshold = computeBonusThreshold();
        for(int i = 0; i < wages.length; i++){
            wages[i] = fixedWage + (itemsSold[i] * perItemWage);
            double baseWage = fixedWage + perItemWage * itemsSold[i];

            if (itemsSold[i] > bonusThreshold) {
                //multiply to get the extra 10% of the current wage
                wages[i] = baseWage * 1.1;
            }
            else {
                wages[i] = baseWage;

            }
        }


    }

}

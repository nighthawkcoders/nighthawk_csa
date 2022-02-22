package com.nighthawk.csa.hacks;

public class Matrix {
    private final int[][] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public String toString() {
        // construct output of matrix using for loops
        // outer loop for row
        StringBuilder output = new StringBuilder();
        for (int[] row : matrix) {
            // inner loop for column
            for (int cell : row) {
                output.append(cell).append(" ");
            }
            output.append("\n"); // new line
        }
        return output.toString();
    }

    static int[][] matix3x3() {
        // declare and initialize a 3x3 matrix
        return new int[][]{ { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    }

    static int[][] numbers() {
        // declare and initialize a 3x3 matrix
        return new int[][]{ { 0, 1 },
                { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 } };
    }

    public static void main(String[] args) {
        Matrix m0 = new Matrix(matix3x3());
        System.out.println(m0);

        Matrix m1 = new Matrix(numbers());
        System.out.println(m1);
    }

}

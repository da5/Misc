package example2;

import java.math.BigInteger;

public class Main {
    /*
        Demonstrates the use of interrupt, join, and daemon.
     */
    public static void main(String[] args) {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        System.out.println("Result : " +
                complexCalculation.calculateResult(
                        BigInteger.valueOf(2),BigInteger.valueOf(30000),
                        BigInteger.valueOf(4),BigInteger.valueOf(5)
                )
        );
    }
}

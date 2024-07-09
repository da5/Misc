package example2;

import java.math.BigInteger;

public class ComplexCalculation {
    private static final int TIMEOUT = 2000;
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); //The program won't wait for its completion
        daemonThread.start();

        thread1.start();
        thread2.start();

        boolean interrupted = false;
        try {
            thread1.join(TIMEOUT);
            if (thread1.isAlive()) {
                thread1.interrupt();
                interrupted = true;
            }
            thread2.join(TIMEOUT);
            if (thread2.isAlive()) {
                thread2.interrupt();
                interrupted = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (interrupted) {
            System.out.println("Interruption of one or more thread(s) which took too long to complete!");
            return BigInteger.ZERO;
        }
        System.out.println("All threads completed execution successfully!");
        return thread1.getResult().add(thread2.getResult());
    }

    private static class DaemonThread extends Thread {
        @Override
        public void run() {
            for (int i=0; i<5; i++) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Daemon going to end in : " + (5-i));
            }
        }
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            while (power.compareTo(BigInteger.ZERO) > 0) {
                if (this.isInterrupted()) {
                    return;
                }
                this.result = this.result.multiply(base);
                power = power.subtract(BigInteger.ONE);
            }
        }

        public BigInteger getResult() { return result; }
    }
}

package example1;

public class PoliceThread extends Thread {
    @Override
    public void run() {
        for (int i=10; i>0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Police car crashed on the way!");
            }
            System.out.println("Police incoming, ETA " + i);
        }
        System.out.println("Police arrived, hackers apprehended!");
        System.exit(0);
    }
}

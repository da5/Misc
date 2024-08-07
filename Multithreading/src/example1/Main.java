package example1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    /*
        A program to demonstrate 2 hacker threads trying to crack a vault
        while a police thread attempting to stop them
     */
    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(Vault.getMaxPassword()));
        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread: threads) {
            thread.start();
        }
    }
}

package example1;

public class DescendingHackerThread extends HackerThread {
    public DescendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = vault.getMaxPassword(); guess>=0; guess--) {
            if (vault.guessPassword(guess)) {
                System.out.println(this.getName() + " has cracked the vault, password : " + guess);
                System.exit(0);
            }
        }
    }
}

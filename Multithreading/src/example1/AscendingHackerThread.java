package example1;

public class AscendingHackerThread extends HackerThread {
    public AscendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = 0; guess<vault.getMaxPassword(); guess++) {
            if (vault.guessPassword(guess)) {
                System.out.println(this.getName() + " has cracked the vault, password : " + guess);
                System.exit(0);
            }
        }
    }
}

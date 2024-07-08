package example1;

public class Vault {
    private final int password;
    private static final int maxPassword = 9999;

    public Vault(int password) {
        this.password = password;
    }

    public static int getMaxPassword() {
        return maxPassword;
    }

    public boolean guessPassword(int guess) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.password==guess;
    }
}

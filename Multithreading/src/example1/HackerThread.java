package example1;

public abstract class HackerThread extends Thread {
    protected final Vault vault;

    public HackerThread(Vault vault) {
        this.vault = vault;
        this.setName(this.getClass().getSimpleName());
    }

    @Override
    public void start() {
        System.out.println("Starting thread " + this.getName());
        super.start();
    }
}

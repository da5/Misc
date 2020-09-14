package practice.Miscellaneous;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTutorial {
    private static void customSleep(long millis) {
        System.out.println(Thread.currentThread().getName() + " going to sleep for " + millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " done sleeping for " + millis);
    }

    private static void completedFutureExample() {
        String message = "Hello World!";
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture(message);
        assert completableFuture.isDone();
        assert message.equals(completableFuture.getNow(null));
    }

    private static void runAsyncExample() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
           assert Thread.currentThread().isDaemon();
           customSleep(5000);
        });
        assert !completableFuture.isDone();
        customSleep(6000);
        assert completableFuture.isDone();
    }

    private static void thenApplyExample() {
        String message = "Hello World!";
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture(message).thenApply(msg -> {
            assert !Thread.currentThread().isDaemon();
            return msg.toUpperCase();
        });
        assert message.toUpperCase().equals(completableFuture.getNow(null));
    }

    private static void thenApplyAsyncExample() {
        String message = "Hello World!";
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture(message).thenApplyAsync(msg -> {
            assert Thread.currentThread().isDaemon();
            return msg.toUpperCase();
        });
        assert completableFuture.getNow(null)==null;
        assert message.toUpperCase().equals(completableFuture.join());
    }

    private static void optionalExample() {
        Optional<String> emptyOptional = Optional.empty();
        assert !emptyOptional.isPresent();

        Optional<String> optional = Optional.of("Hello World!");
        assert optional.isPresent();
        assert "Hello World!".equals(optional.get());

        Optional<String> nullableOptional = Optional.ofNullable("Hello World!");
        assert nullableOptional.isPresent();
        assert "Hello World!".equals(nullableOptional.get());

        nullableOptional = Optional.ofNullable(null);
        assert !nullableOptional.isPresent();

        Queue<String> queue = new PriorityQueue<>((x,y) -> y.length()-x.length());
        NavigableMap<String, Integer> map = new TreeMap<>((x,y) -> y.length()-x.length());
    }

    public static void main(String[] args) {
//        completedFutureExample();
//        runAsyncExample();
        thenApplyExample();
        thenApplyAsyncExample();
        optionalExample();
    }
}

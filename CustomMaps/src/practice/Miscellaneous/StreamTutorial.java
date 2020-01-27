package practice.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTutorial {
    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class PendingTransaction {
        long id;

        public PendingTransaction(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }

    static class ProcessedTransaction {
        String id;
        Optional<String> status;

        public ProcessedTransaction(String id, Optional<String> status) {
            this.id = id;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public Optional<String> getStatus() {
            return status;
        }
    }

    private static List<String> getList() {
        return Arrays.asList("c3", "a1", "b1", "c2", "c1");
    }

    private static List<Person> getPersonList() {
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 13));
        return persons;
    }

    private static List<Foo> getFooBarList() {
        List<Foo> fooList = new ArrayList<>();

        IntStream.range(1,4)
                .forEach(i -> fooList.add(new Foo("Foo"+i)));

        fooList.forEach(
                foo -> IntStream.range(1,4).forEach(
                        i -> foo.bars.add(new Bar("Bar"+i))
                )
        );

        return fooList;
    }

    private static void f1() {
        Stream<String> stream = getList().stream()
                .filter(str -> str.startsWith("c"))
                .map(String::toUpperCase)
                .sorted((s1, s2) -> -1*s1.compareTo(s2));
        print(stream);
    }

    private static void f2() {
        IntStream.range(1,10)
                .map(n -> 3 * n - 3)
                .max()
                .ifPresent(System.out::println);
    }

    private static void f3() {
        Stream<String> stream = getList().stream()
                .map(str -> {
                    System.out.println("map : " + str);
                    return "_"+str;
                });
        print(stream);
    }

    private static void f4() {
        getList().stream()
                .map(str -> {
                    System.out.println("map : " + str);
                    return "_"+str;
                }).anyMatch(str -> {
                    System.out.println("match : " + str);
                    return str.startsWith("_b");
        });
    }

    private static void f5() {
        Supplier<Stream<String>> supplier = () -> getList().stream();

        print(supplier.get());
        System.out.println("------");
        sortedPrint(supplier.get());
    }

    private static void f6() {
        Supplier<Stream<Person>> supplier = () -> getPersonList().stream();

        System.out.println(supplier.get()
                .filter(p -> p.name.startsWith("P"))
                .collect(Collectors.toList())
        );

        System.out.println(supplier.get()
                .collect(Collectors.groupingBy(p -> p.age))
        );

        System.out.println(supplier.get()
                .collect(Collectors.averagingInt(p -> p.age))
        );

        System.out.println(supplier.get()
                .collect(Collectors.summarizingInt(p -> p.age))
        );

        System.out.println(supplier.get()
                .filter(p -> p.age>18)
                .map(p -> p.name)
                .collect(Collectors.joining(",", "Persons ", " are of legal age."))
        );

    }

    private static void f7() {
        Supplier<Stream<Stream<Bar>>> barSupplier =
                () -> getFooBarList().stream()
                        .map(foo -> foo.bars.stream());

        System.out.println(
                barSupplier.get().flatMap(Function.identity())
                        .map(bar -> bar.name)
                        .collect(Collectors.toList())
        );

        System.out.println(
                barSupplier.get().map(
                        barStream -> barStream.map(bar -> bar.name)
                                .collect(Collectors.joining("|"))
                ).collect(Collectors.toList())
        );
    }

    private static void print(Stream<String> stream) {
        stream.forEach(System.out::println);
    }

    private static void sortedPrint(Stream<String> stream) {
        stream.sorted()
                .forEach(System.out::println);
    }

    private static Stream<PendingTransaction> reconcileOld(Stream<PendingTransaction> pending,
                                         Stream<Stream<ProcessedTransaction>> processed) {
        if(pending==null && processed==null) {
            return Stream.empty();
        }

        Stream<Long> filteredProcessedId = processed
                .flatMap(p -> p)
                .filter(Objects::nonNull)
                .filter(p -> p.getStatus()!=null && "DONE".equals(p.getStatus().orElse(null)))
                .filter(p -> p.getId()!=null && p.getId().length()>0)
                .map(p -> Long.parseLong(p.getId()));
        return pending.filter(p -> filteredProcessedId.anyMatch(pr -> pr.equals(p.getId())));
    }

    private static Stream<PendingTransaction> reconcile(Stream<PendingTransaction> pending,
                                                        Stream<Stream<ProcessedTransaction>> processed) {
        if(pending==null || processed==null) {
            return Stream.empty();
        }

        Set<Long> filteredProcessedId = processed
//                .filter(Objects::nonNull)
                .flatMap(p -> p)
                .filter(Objects::nonNull)
                .filter(p -> p.getStatus().isPresent() && "DONE".equals(p.getStatus().get()))
                .filter(p -> p.getId()!=null && !p.getId().isEmpty())
                .map(p -> Long.parseLong(p.getId()))
                .collect(Collectors.toSet());

        return pending
                .filter(Objects::nonNull)
                .filter(p -> filteredProcessedId.contains(p.getId()));
    }

    private static void testReconcile() {
        System.out.println(reconcile(null, null).count());
        System.out.println(reconcile(null, Stream.empty()).count());
        System.out.println(reconcile(Stream.empty(), null).count());

        Stream<PendingTransaction> pending = Stream.of(
                null,
                new PendingTransaction(1),
                new PendingTransaction(2),
                null,
                new PendingTransaction(3),
                new PendingTransaction(4),
                new PendingTransaction(5),
                null
        );

        Stream<Stream<ProcessedTransaction>> processed = Stream.of(
            null,
            Stream.of(
                    new ProcessedTransaction("2", Optional.empty()),
                    new ProcessedTransaction("2", Optional.of("")),
                    null,
                    new ProcessedTransaction("3", Optional.of("DONE"))
            ),
            Stream.of(
                    new ProcessedTransaction("2", Optional.of("DONE")),
                    new ProcessedTransaction("1", Optional.of("DONE"))
            ),
            null,
            Stream.of(
                    null,
                    new ProcessedTransaction("2", Optional.of("")),
                    null,
                    new ProcessedTransaction("1", Optional.empty()),
                    null,
                    new ProcessedTransaction("", Optional.of("DONE")),
                    new ProcessedTransaction(null, Optional.of("DONE")),
                    new ProcessedTransaction("2", Optional.of("DONE")),
                    new ProcessedTransaction("5", Optional.of("DONE"))
            ),
            null
        );

        System.out.println(reconcile(pending, processed).count());
    }

    public static void main(String[] args) {
        testReconcile();

    }
}

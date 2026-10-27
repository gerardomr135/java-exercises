package com.example.university;

import com.example.university.lambdas.Foo;
import com.example.university.repo.CourseRepo;
import com.example.university.repo.StaffRepo;
import com.example.university.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Component
public class Prepare implements CommandLineRunner {
    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private CourseRepo courseRepo;

    private String value = "Enclosing scope value";

    private static final Logger log = LoggerFactory.getLogger(Prepare.class);

    @Override
    public void run(String... args) throws Exception {
        streams();
        System.out.println();
        staffRepo.findAll().forEach(System.out::println);
        studentRepo.findAll().forEach(System.out::println);
        courseRepo.findAll().forEach(System.out::println);
    }

    private static void streams() throws IOException {
        Stream<String> streamEmpty = Stream.empty();
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();
        Stream<String> streamOfArray = Stream.of("a", "b", "c");
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);
        Stream<String> streamBuilder =
                Stream.<String>builder().add("a").add("b").add("c").build();

        Stream<String> streamGenerated =
                Stream.generate(() -> "element").limit(10);
        streamGenerated.forEach(v -> System.out.print(v + " "));
        System.out.println();

        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        streamIterated.forEach(v -> System.out.print(v + " "));
        System.out.println();

        IntStream intStream = IntStream.range(1, 3);
        intStream.forEach(v -> System.out.print(v + " "));
        System.out.println();

        LongStream longStream = LongStream.rangeClosed(1, 3);
        longStream.forEach(v -> System.out.print(v + " "));
        System.out.println();

        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
        doubleStream.forEach(v -> System.out.print(v + " "));
        System.out.println();

        IntStream streamOfChars = "abc".chars();
        streamOfChars.forEach(v -> System.out.print(v + " "));
        System.out.println();
//        Path path = Paths.get("C:\\file.txt");
//        Stream<String> streamOfStrings = Files.lines(path);
//        Stream<String> streamWithCharset =
//                Files.lines(path, StandardCharsets.UTF_8);

//        Stream<String> stream =
//                Stream.of("a", "b", "c").filter(element -> element.contains("b"));
//        Optional<String> anyElement = stream.findAny();
//        Optional<String> firstElement = stream.findFirst();Optional<String> firstElement = stream.findFirst();

        List<String> elements =
                Stream.of("a", "b", "c").filter(element -> element.contains("b"))
                        .toList();
        Optional<String> anyElement = elements.stream().findAny();
        System.out.println(anyElement.get());

        elements.forEach(v -> System.out.print(v + " "));
        System.out.println();

        Optional<String> firstElement = elements.stream().findFirst();
        firstElement.ifPresent(System.out::println);

        // PIPELINES...
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        long size = list.stream().skip(1)
                .count();
        System.out.println(size);
        System.out.println();

        Optional<String> stream = list.stream().filter(element -> {
            log.info("filter() was called");
            return element.contains("2");
        }).map(element -> {
            log.info("map() was called");
            return element.toUpperCase();
        }).findFirst();
        System.out.println();

        OptionalInt reduced =
                IntStream.range(1, 4).reduce(Integer::sum);
        System.out.println(reduced.getAsInt()); // 6

        int reducedTwoParams =
                IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        System.out.println(reducedTwoParams); // 16
        System.out.println();

        int reducedParams = Stream.of(1, 2, 3)
                .parallel()
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    log.info("combiner was called");
                    return a + b;
                });

        System.out.println(reducedParams);
        System.out.println();

        List<String> givenList = Arrays.asList("a", "bb", "ccc", "dd");

//        List<String> result = givenList.stream()
//                .collect(toCollection(LinkedList::new));

        Map<String, Integer> result = givenList.stream()
                .collect(toMap(Function.identity(), String::length));
        System.out.println(result);

        String resultj = givenList.stream()
                .collect(joining(" ", "PRE-", "-POST"));
        System.out.println(resultj);

        Optional<String> resultk = givenList.stream()
                .collect(maxBy(Comparator.naturalOrder()));
        System.out.println(resultk);

        Map<Integer, Set<String>> resultl = givenList.stream()
                .collect(groupingBy(String::length, toSet()));
        System.out.println(resultl);
    }

    public void lambdaExpressionsAndFunctionalInterfaces() {
        Foo fooIC = new Foo() {
            String value = "Inner class value";

            @Override
            public String method(String string) {
                return this.value;
            }
        };
        String resultIC = fooIC.method("");

        Foo fooLambda = parameter -> {
            String value = "Lambda value";
            return this.value;
        };
        String resultLambda = fooLambda.method("");

        System.out.println("Results: resultIC = " + resultIC +
                ", resultLambda = " + resultLambda);
    }
}

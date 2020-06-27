package LambdasAndStreams_6.UseStreamsJudiciously_45.BadUseOfStreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        Path dictionary = Paths.get(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        /*
            This is a pain in the ass to read.
            - That means it is going to be hard to maintain.
            - That means it is likely to be error-prone and buggy
            - That means it is likely to piss off customers
            - I think I've said all that is necessary :)

            The killer is the garbage nested into the collect() call.
         */
        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(
                    groupingBy(word -> word.chars().sorted()
                            .collect(
                                    StringBuilder::new,
                                    (sb, c) -> sb.append((char) c), StringBuilder::append).toString())
            ).values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .map(group -> group.size() + ": " + group)
                    .forEach(System.out::println);
        }
    }
}

package LambdasAndStreams_6.UseStreamsJudiciously_45.GoodUseOfStreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Anagrams {
    public static void main(String[] args) throws IOException {
        Path dictionary = Paths.get(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        /*
            Readability is improved by separating the work being done inside the collect(groupingBy()) call.

            This is 2 separate streams pipelines stitched together.

            The Stream variable is named "words", which implies that each element is a word.
            - words is the source stream, and collect is the terminal operation (i.e. no intermediate operations)
            - values().stream() is the source of a new stream.
         */
        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(groupingBy(Anagrams::alphabetize))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));
        }
    }

    private static String alphabetize(String str) {
        char[] c = str.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }
}

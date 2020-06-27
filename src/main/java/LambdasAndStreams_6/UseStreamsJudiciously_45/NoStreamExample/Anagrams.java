package LambdasAndStreams_6.UseStreamsJudiciously_45.NoStreamExample;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Anagrams {
    public static void main(String[] args) throws IOException {
        File dictionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        Map<String, Set<String>> groups = new HashMap<>();

        /*
            Read words from a dictionary
         */
        try (Scanner scanner = new Scanner(dictionary)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                /*
                    Builds the map:
                        key = word with letters alphabetized (alphagram)
                        value = set containing all of the words that share the same "alphagram"

                        computeIfAbsent() is a powerful method introduced in Java 8
                        - if key is present, just return the value associated
                        - else method computes a value by applying the function object to the key
                            - associates value from function object w/ key
                            - returns computed value

                        - This simplifies map implementation where multiple values are associated with each key.
                 */
                groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
            }
        }

        /*
            Iterate through group and print anagram groups whose size meet the specified param
         */
        for (Set<String> group : groups.values()) {
            if (group.size() >= minGroupSize) {
                System.out.println(group.size() + ": " + group);
            }
        }
    }

    private static String alphabetize(String str) {
        char[] c = str.toCharArray();
        Arrays.sort(c);
        return new String(c);
    }
}

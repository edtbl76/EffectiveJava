package EnumsAndAnnotations_5.UseOverrideConsistently_40.BuggyCode;

import java.util.HashSet;
import java.util.Set;

public class Bigram {
    private final char first;
    private final char second;

    public Bigram(char first, char second) {
        this.first = first;
        this.second = second;
    }

    /*
        Without the @Override annotation here, we don't detect the fact that this is a bug.
        the super type equals() takes a parameter of type Object.

        Since this equals method tests for IDENTITY, it thinks that each of the ten copies in our final example
        are distinct from each other.
     */
    public boolean equals(Bigram bigram) {
        return bigram.first == first && bigram.second == second;
    }

    public int hashCode() {
        return 31 * first + second;
    }

    public static void main(String[] args) {
        // Set up a HashSet
        Set<Bigram> set = new HashSet<>();

        // Bounded loop of 10 iterations (which means that we will repeat the work in the nested loop 10 times)
        for (int i = 0; i < 10; i++) {
            /*
                This iterates through all of the characters of the alphabet.
                - for each iteration we add the same character twice to our "Bigram" object.
             */
            for (char ch = 'a'; ch <= 'z'; ch++) {
                set.add(new Bigram(ch, ch));
            }
            System.out.println(set.size());
        }
    }
}

// Actual Output
/*
26
52
78
104
130
156
182
208
234
260
 */

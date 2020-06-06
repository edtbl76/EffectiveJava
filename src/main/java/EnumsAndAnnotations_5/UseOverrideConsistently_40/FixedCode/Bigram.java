package EnumsAndAnnotations_5.UseOverrideConsistently_40.FixedCode;

import com.google.common.base.Objects;

import java.util.HashSet;
import java.util.Set;

public class Bigram {
    private final char first;
    private final char second;

    public Bigram(char first, char second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bigram bigram = (Bigram) o;
        return first == bigram.first &&
                second == bigram.second;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(first, second);
    }

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) {
        Set<Bigram> set = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                set.add(new Bigram(ch, ch));
            }
            System.out.println(set.size());
        }

    }
}

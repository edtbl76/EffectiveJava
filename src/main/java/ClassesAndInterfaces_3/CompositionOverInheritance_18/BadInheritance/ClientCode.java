package ClassesAndInterfaces_3.CompositionOverInheritance_18.BadInheritance;

import java.util.List;

public class ClientCode {

    public static void main(String[] args) {
        InstrumentedHashSet<String> strings = new InstrumentedHashSet<>();
        strings.addAll(List.of("Snap", "Crackle", "Pop"));

        // THIS IS BROKEN!
        /*
            This returns six instead of 3
            - This is because HashSet's addAll() method is implemented on top of its add() method.
         */
        System.out.println(strings.getAddCount());
    }
}

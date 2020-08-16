package GeneralProgramming_8.InterfacesVReflection_65.EffectiveReflectionExample1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public class EffectiveReflectionExample1 {

    private static void fatalError(String msg) {
        System.err.println(msg);
        System.exit(1);
    }

    // Reflective instantiation w/ interface access
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // translate the class name into a Class object
        Class<? extends Set<String>> clazz = null;

        try {
            clazz = (Class<? extends Set<String>>)      // unchecked cast
            Class.forName(args[0]);
        } catch (ClassNotFoundException ex) {
            fatalError("Class not found.");
        }

        // Get the constructor
        Constructor<? extends Set<String>> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException ex) {
            fatalError("No parameterless constructor");
        }

        // Instantiate the set
        Set<String> set = null;

        try{
            set = constructor.newInstance();
        } catch (IllegalAccessException ex) {
            fatalError("Constructor not accessible");
        } catch (InstantiationException e) {
            fatalError("Class not instantiable");
        } catch (InvocationTargetException e) {
            fatalError("Constructor threw " + e.getCause());
        } catch (ClassCastException e) {
            fatalError("Class doesn't implement Set");
        }

        // Test
        set.addAll(Arrays.asList(args).subList(1, args.length));
        System.out.println(set);
    }
}

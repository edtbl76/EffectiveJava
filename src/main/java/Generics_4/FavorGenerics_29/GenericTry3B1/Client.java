package Generics_4.FavorGenerics_29.GenericTry3B1;

public class Client {

    public static void main(String[] args) {
        Stack2<String> stack = new Stack2<>();
        for (String arg: args) {
            stack.push(arg);
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop().toUpperCase());
        }
    }
}

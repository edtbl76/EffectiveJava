package Methods_7.OverloadingJudiciously_52.Overriding;

import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<Wine> wineList = List.of(
                new Wine(), new SparklingWine(), new Champagne()
        );

        wineList.forEach(System.out::println);
    }
}

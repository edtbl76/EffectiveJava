package LambdasAndStreams_6.UseStreamsJudiciously_45.IterationVsStreams.CartesianProduct;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamBasedCartesianProduct {

    private static List<Card> newDeck() {
       return Stream.of(Card.Suit.values())
               /*
                   flatMap() is an intermedate operation
                   - it converts each element from the source or preceding intermedate operator into it's own stream
                   - then it concatenates all of the streams into a new stream. ("flattens" them)
                        - usually the purpose is to be able to create a mini internal pipeline inside the flatMap
                        to perform some form of processing.

                        In this case, we are taking a Stream of suit values which are mapped to a pipeline we
                        invoke internally that maps each suit to each rank.

                        VERY fast Cartesian product.

                */
               .flatMap(suit -> Stream.of(Card.Rank.values())
                       .map(rank -> new Card(suit, rank)))
               .collect(Collectors.toList());
    }
}



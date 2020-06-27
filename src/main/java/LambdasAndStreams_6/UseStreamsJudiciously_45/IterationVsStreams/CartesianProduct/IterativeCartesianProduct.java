package LambdasAndStreams_6.UseStreamsJudiciously_45.IterationVsStreams.CartesianProduct;

import java.util.ArrayList;
import java.util.List;

public class IterativeCartesianProduct {

    private static List<Card> newDeck() {
        List<Card> result = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                result.add(new Card(suit, rank));
            }
        }
        return result;
    }
}



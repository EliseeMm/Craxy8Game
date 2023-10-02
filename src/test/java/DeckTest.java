import org.cards.Card;
import org.cards.DeckOfCards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    private DeckOfCards deckOfCards;
    @BeforeEach
    void createDeck(){
        deckOfCards = new DeckOfCards();
    }
    @Test
    void deckLength(){
        assertEquals(54,deckOfCards.getDeckOfCards().size());
    }

    @Test
    void suitCheck(){
        int hearts = 0;
        int clubs = 0;
        int diamonds = 0;
        int spades = 0;
        int jokers = 0;

        Stack<Card> cards  = deckOfCards.getDeckOfCards();

        while (!cards.empty()){
            Card card =  cards.pop();
            switch (card.suit()){
                case "H":
                    hearts += 1;
                    break;
                case "S":
                    spades+= 1;
                    break;
                case "C":
                    clubs += 1;
                    break;
                case "D":
                    diamonds += 1;
                    break;
                default:
                    jokers += 1;
                    break;
            }
        }
        assertEquals(13,hearts);
        assertEquals(13,clubs);
        assertEquals(13,diamonds);
        assertEquals(13,spades);
        assertEquals(2,jokers);

    }
}

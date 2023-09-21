import org.cards.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CardTest {
    private Card card;
    @BeforeEach
     void createCard(){
        card = new Card("Spades", "A");
    }

    @Test
    void cardGetters(){
        assertEquals("A",card.number());
        assertEquals("Spades",card.suit());
    }

    @Test
    void stringFormat(){
        assertEquals("A of Spades",card.toString());
    }

    @Test
    void equalsToAnotherCard(){
        Card newCard = new Card("Spades","A");
        assertEquals(card,newCard);
    }
    @Test
    void notEqual(){
        Card newCard = new Card("Clubs","2");
        assertNotEquals(card,newCard);
    }
}

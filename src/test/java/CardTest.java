import org.cards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CardTest {
    private Card card;
    @BeforeEach
     void createCard(){
        card = new Card("S", "A");
    }

    @Test
    void cardGetters(){
        assertEquals("A",card.number());
        assertEquals("S",card.suit());
    }

    @Test
    void stringFormat(){
        assertEquals("A of S",card.toString());
    }

    @Test
    void equalsToAnotherCard(){
        Card newCard = new Card("S","A");
        assertEquals(card,newCard);
    }
    @Test
    void notEqual(){
        Card newCard = new Card("C","2");
        assertNotEquals(card,newCard);
    }
}

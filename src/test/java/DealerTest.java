import org.cards.Card;
import org.cards.Dealer.Dealer;
import org.cards.DeckOfCards;
import org.cards.Players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class DealerTest {

    private DeckOfCards deckOfCards;
    private Stack<Card>  originalStack;
    private Dealer dealer;

    @BeforeEach
    void generateCardsDeck(){
        deckOfCards = new DeckOfCards();
        originalStack = deckOfCards.getDeckOfCards();
        dealer = new Dealer(deckOfCards);
    }

    @Test
    void shuffleTest(){
        dealer.shuffleCards();
        Stack<Card> shuffledCards = dealer.getDeckOfCards();
        for(int i = 0; i <= deckOfCards.getDeckOfCards().size();i++){
            if(!shuffledCards.pop().equals(originalStack.pop())){
                assertNotEquals(shuffledCards.pop(), originalStack.pop());
            }
        }
    }

    @Test
    void dealtCards2Players(){
        Player player1 = new Player("John");
        Player player2 = new Player("Becky");

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        dealer.dealCards(players);

        assertEquals(7,player1.getCardsInHand().size());
        assertEquals(7,player2.getCardsInHand().size());

        assertEquals(40,dealer.getDeckOfCards().size());

    }

    @Test
    void dealtCards4Players(){
        Player player1 = new Player("A");
        Player player2 = new Player("B");
        Player player3 = new Player("c");
        Player player4 = new Player("D");

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        dealer.dealCards(players);

        assertEquals(5,player1.getCardsInHand().size());
        assertEquals(5,player2.getCardsInHand().size());
        assertEquals(5,player3.getCardsInHand().size());
        assertEquals(5,player4.getCardsInHand().size());

    }
}

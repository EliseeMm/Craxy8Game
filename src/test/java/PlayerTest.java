import org.cards.Card;
import org.cards.Dealer.Dealer;
import org.cards.DeckOfCards;
import org.cards.Players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private  Player player1;
    private  Dealer dealer;


    @BeforeEach
    void initialize(){
        player1 = new Player("One");
        DeckOfCards deckOfCards = new DeckOfCards();
        dealer = new Dealer(deckOfCards);
    }
    @Test
    void playerPlaysCard(){
        dealer.shuffleCards();
        dealer.dealCards(List.of(player1));

        //Check the first card the player has
        Card player1FirstCard = player1.getCardsInHand().get(0);

        //plays the first card they have
        player1.placeCardDown(player1FirstCard);

        // checks that the first card is no longer in the players hand
        assertFalse(player1.getCardsInHand().contains(player1FirstCard));
    }

    @Test
    void playerPickUpCardFromStack(){
        // dealer shuffles the card
        dealer.shuffleCards();

        // dealer gives player cards
        dealer.dealCards(List.of(player1));

        // save the initial set of cards
        ArrayList<Card> cardsInitial = new ArrayList<>(player1.getCardsInHand());

        // cards left after the deal
        Stack<Card> remainingCards = dealer.getDeckOfCards();

        // player picks up card from remaining stack
        Card pickedUpCard = player1.pickUpCards(remainingCards);

        // save the set of cards after picking
        ArrayList<Card> cardsFinal = new ArrayList<>(player1.getCardsInHand());

        // Checks that the player did not initially have this card
        assertFalse(cardsInitial.contains(pickedUpCard));

        // Checks that the player now has this card
        assertTrue(cardsFinal.contains(pickedUpCard));

    }
}
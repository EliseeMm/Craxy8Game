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
    private final Player player1 = new Player();
    private final DeckOfCards deckOfCards = new DeckOfCards();
    private final Dealer dealer = new Dealer(deckOfCards);


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
        dealer.shuffleCards();
        dealer.dealCards(List.of(player1));

        ArrayList<Card> cardsInitial = player1.getCardsInHand();
        System.out.println(cardsInitial);

        Stack<Card> remainingCards = dealer.getDeckOfCards();

        Card pickedUpCard = player1.pickUpCards(remainingCards);
        System.out.println(pickedUpCard);

        ArrayList<Card> cardsFinal = player1.getCardsInHand();
        System.out.println(cardsFinal);

        // Checks that the player did not initially have this card
        System.out.println(cardsInitial.contains(pickedUpCard));
        assertFalse(cardsInitial.contains(pickedUpCard));

        // Checks that the player now has this card
        assertTrue(cardsFinal.contains(pickedUpCard));

    }
}
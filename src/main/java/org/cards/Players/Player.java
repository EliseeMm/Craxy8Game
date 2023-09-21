package org.cards.Players;

import org.cards.Card;
import java.util.ArrayList;
import java.util.Stack;

/*
 * Represents a player playing a game
 */
public class Player {
    private final ArrayList<Card> cardsInHand = new ArrayList<>();

    // Used when a player picks up cards or is dealt cards
    public Card pickUpCards(Stack<Card> deck){
        Card cardToPickUp = deck.pop();
        cardsInHand.add(cardToPickUp);
        return cardToPickUp;
    }

    // Removes a card from the player hand
    public void placeCardDown(Card card){
        cardsInHand.remove(card);
    }

    // Check the cards a player still has
    public ArrayList<Card> getCardsInHand(){
        return cardsInHand;
    }

    public void getCardFromDealer(Card card) {
        cardsInHand.add(card);
    }
}

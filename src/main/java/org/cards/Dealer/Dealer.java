package org.cards.Dealer;

import org.cards.Card;
import org.cards.DeckOfCards;
import org.cards.Players.Player;
import java.util.*;

/*
 * Dealer, that will shuffle the deck of cards and hand them out to players
 */
public class Dealer {
    private final Stack<Card> cardStack;

    public Dealer(DeckOfCards deckOfCards){
        this.cardStack = deckOfCards.getDeckOfCards();

    }

    public void shuffleCards(){
        Collections.shuffle(cardStack);

    }

    public void dealCards(List<Player> players){
        int cardsPerPlayer;
        if (players.size() == 2){
            cardsPerPlayer = 7;
        } else {
            cardsPerPlayer = 5;
        }

        for(int cardNum = 1; cardNum <= cardsPerPlayer; cardNum++) {
            for (Player player : players
            ) {
                Card card = cardStack.pop();
                player.getCardFromDealer(card);
            }
        }
    }

    public Card setCentreCard(){
        return cardStack.pop();
    }
    public Stack<Card> getDeckOfCards(){
        return cardStack;
    }
}

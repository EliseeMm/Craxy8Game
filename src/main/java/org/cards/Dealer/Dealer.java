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
        List<Card>  cards  = new ArrayList<>();

        // Pops a card from the deck and adds it to a list while the deck is not empty
        while (!cardStack.empty()){
            Card card = cardStack.pop();
            cards.add(card);
        }

        // Shuffles the list of cards
        Collections.shuffle(cards);

        // Add the cards back to the deck
        for (Card card: cards
             ) {
            cardStack.push(card);
        }

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

    public Stack<Card> getDeckOfCards(){
        return cardStack;
    }
}

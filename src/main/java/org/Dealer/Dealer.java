package org.Dealer;

import org.Players.Player;
import org.cards.Card;
import org.cards.DeckOfCards;

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
        ArrayList<Card> checkedCards = new ArrayList<>();
        Card card;
        do {
            card  = cardStack.pop();
            checkedCards.add(card);
        } while (card.number().equals("8") || card.number().equals("7") || card.number().equals("2") ||
                card.number().equals("A") || card.number().equals("J") || card.equals(new Card("J","1"))||
                card.equals(new Card("J","2")));

        for (Card c: checkedCards
             ) {
            cardStack.push(c);
        }
        return card;
    }
    public Stack<Card> getDeckOfCards(){
        return cardStack;
    }
}

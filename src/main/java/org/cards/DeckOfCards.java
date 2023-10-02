package org.cards;

import java.util.Stack;


/*
 * Represents a deck of cards
 */
public class DeckOfCards {

    private final Stack<Card> deckOfCards;

    public DeckOfCards(){
        deckOfCards = generateDeck();
    }

    private Stack<Card> generateDeck(){
        Stack<Card> cards = new Stack<>();
        String[] suits = {"H","D","C","S"};
        String[] numbers = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

        String[] jokers = {"1","2"};

        for (String suit: suits
             ) {
            for (String number: numbers
                 ) {
                Card card = new Card(suit,number);
                cards.push(card);
            }
        }

        for (String joker: jokers){
            Card card = new Card("J",joker);
            cards.push(card);
        }
        return cards;
    }

    public Stack<Card> getDeckOfCards(){
        return deckOfCards;
    }
}

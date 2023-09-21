package org.cards;

/*
 * Represents a card record, with suits and number
 */
public record Card(String suit, String number) {

    @Override
    public String toString() {
        return this.number + " of " + this.suit;
    }
}

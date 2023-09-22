package org.cards;

import org.cards.Players.Player;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class GamePlay {
    private final ArrayList<Player> gamePlayers;
    private final Stack<Card> stockPile;
    private final Stack<Card> discardPile;
    private Player currentPlayer;
    private Card centreCard;
    ArrayList<Card> cardsToHandToPlayer = new ArrayList<>();
    public GamePlay(ArrayList<Player> gamePlayers,Stack<Card> stockPile,Card centreCard){
        this.gamePlayers = gamePlayers;
        this.stockPile = stockPile;
        discardPile = new Stack<>();
        discardPile.push(centreCard);
        this.centreCard = discardPile.peek();
        currentPlayer = gamePlayers.get(0);
    }

    public ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public Stack<Card> getStockPile() {
        return stockPile;
    }

    public Stack<Card> getDiscardPile(){
        return discardPile;
    }

    public boolean play(JSONObject action){
        String name = action.getString("name");
        if(currentPlayer.getPlayerName().equals(name)){
            switch (action.getString("action")){
                case "discard":
                    JSONObject cardJSON = action.getJSONObject("card");
                    Card card = new Card(cardJSON.getString("suit"),cardJSON.getString("number"));
                    cardPlacedOnDiscardPile(currentPlayer,card);
                    break;
                case "accept":
                    acceptCards();
                    break;
                case "reject":
                    rejectCards();
                    break;
                case "pick":
                    playerPicksUpCard(currentPlayer);
                    break;
            }

            return true;
        }
        return false;
    }

    /*
    places the players card on the discard  pile if the card is a 2
     */
    private boolean cardPlacedOnDiscardPile(Player player,Card card){
        player.placeCardDown(card);
        if(card.number().matches("\\d+")) {
            if (Integer.parseInt(card.number()) == 2) {
                makingAPlayerPickUp(2);
            }
        } else if (card.number().equals("J")) {
            reverseList();
        }

        discardPile.push(card);
        centreCard = discardPile.peek();
        return true;
    }
    private void playerPicksUpCard(Player player){
        Card cardToPick = stockPile.pop();
        player.pickUpCard(cardToPick);
        currentPlayer = gamePlayers.get(gamePlayers.indexOf(currentPlayer)+1);
    }

    // if a player plays the 7 card it skips the player immediately after them
    private void skipTheNextPlayer(){
        currentPlayer = gamePlayers.get(gamePlayers.indexOf(currentPlayer)+2);
    }

    // if a player plays the J/Jack card it reverses the order of the players
    private void reverseList(){
        Collections.reverse(gamePlayers);
    }

    private void makingAPlayerPickUp(int cardsToPick){
        for (int num = 1; num <= cardsToPick;num++){
            Card card = stockPile.pop();
            cardsToHandToPlayer.add(card);
        }
        currentPlayer = gamePlayers.get(gamePlayers.indexOf(currentPlayer)+1);
    }
    private void rejectCards(){
        for (Card card:cardsToHandToPlayer
             ) {
            stockPile.push(card);
        }
        cardsToHandToPlayer.clear();
    }

    private void acceptCards(){
        for (Card card: cardsToHandToPlayer
             ) {
            currentPlayer.pickUpCard(card);
        }
        cardsToHandToPlayer.clear();
    }

    public Card getCentreCard() {
        return centreCard;
    }
}

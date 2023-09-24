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
        this.currentPlayer = gamePlayers.get(0);
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
                case "switch":

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
        if(card.number().equals(centreCard.number()) || card.suit().equals(centreCard.suit())) {
            if (card.number().matches("\\d+") && card.number().equals("2")) {
                    makingAPlayerPickUp(2);
                }
            else if (card.number().equals("J")) {
                reverseList();
            } else if (card.number().equals("7")) {
                skipTheNextPlayer();
            }
            discardPile.push(card);
            centreCard = discardPile.peek();
            return true;
            }
        else if (card.number().equals("Joker")) {
            makingAPlayerPickUp(5);
            discardPile.push(card);
            centreCard = discardPile.peek();
            return true;
        }
        return false;
    }
    private void playerPicksUpCard(Player player){
        Card cardToPick = stockPile.pop();
        player.pickUpCard(cardToPick);
        nextPlayer();
    }

    // if a player plays the 7 card it skips the player immediately after them
    private void skipTheNextPlayer(){
        int indexOfCurrentPlayer = gamePlayers.indexOf(currentPlayer);
        if(indexOfCurrentPlayer == gamePlayers.size()-1 && gamePlayers.size() > 2){
            currentPlayer = gamePlayers.get(1);
        } else if (indexOfCurrentPlayer == gamePlayers.size()-1) {
            currentPlayer = gamePlayers.get(indexOfCurrentPlayer);
        } else if((indexOfCurrentPlayer+2) > gamePlayers.size()-1){
            currentPlayer = gamePlayers.get(0);
        }else {
            currentPlayer = gamePlayers.get(gamePlayers.indexOf(currentPlayer) + 2);
        }
    }

    // if a player plays the J/Jack card it reverses the order of the players
    private void reverseList(){
        int indexOfCurrentPlayer = gamePlayers.indexOf(currentPlayer);

        if(indexOfCurrentPlayer == 0){
            currentPlayer = gamePlayers.get(gamePlayers.size()-1);
        }
        else {
            currentPlayer = gamePlayers.get(indexOfCurrentPlayer - 1);
        }
        Collections.reverse(gamePlayers);

    }

    private void makingAPlayerPickUp(int cardsToPick){
        for (int num = 1; num <= cardsToPick;num++){
            Card card = stockPile.pop();
            cardsToHandToPlayer.add(card);
        }
        nextPlayer();
    }
    private void rejectCards(){
        for (Card card:cardsToHandToPlayer
             ) {
            stockPile.push(card);
        }
        cardsToHandToPlayer.clear();
        nextPlayer();
    }

    private void acceptCards(){
        for (Card card: cardsToHandToPlayer
             ) {
            currentPlayer.pickUpCard(card);
        }
        cardsToHandToPlayer.clear();

        nextPlayer();
    }

    public Card getCentreCard() {
        return centreCard;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void requestSuit(){

    }

    public void nextPlayer(){
        int indexOfCurrentPlayer = gamePlayers.indexOf(currentPlayer);
        if(indexOfCurrentPlayer == gamePlayers.size()-1){
            currentPlayer = gamePlayers.get(0);
        }else {
            currentPlayer = gamePlayers.get(gamePlayers.indexOf(currentPlayer) + 1);
        }
    }
}

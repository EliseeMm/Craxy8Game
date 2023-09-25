package org.cards;

import org.cards.Players.Player;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GamePlay {
    private final ArrayList<Player> gamePlayers;
    private final Stack<Card> stockPile;
    private final Stack<Card> discardPile;
    private Player currentPlayer;
    private Card centreCard;
    ArrayList<Card> cardsToHandToPlayer = new ArrayList<>();
    private String suitWanted = "";
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
        String request = action.getString("action");
        if(request.equals("quit")){
            removePlayer(name);
        }
        else if(currentPlayer.getPlayerName().equals(name)){
            switch (action.getString("action")) {
                case "discard" -> {
                    return cardPlacedOnDiscardPile(currentPlayer, action);
                }
                case "accept" -> {
                    return acceptCards();
                }
                default-> {
                    return playerPicksUpCard(currentPlayer);
                }
            }
        }
        return false;
    }

    /*
    places the players card on the discard  pile if the card is a 2
     */
    private boolean cardPlacedOnDiscardPile(Player player,JSONObject action){
        JSONObject cardJSON = action.getJSONObject("card");
        Card card = new Card(cardJSON.getString("suit"), cardJSON.getString("number"));
        if(!suitWanted.isEmpty() && matchesSuitWanted(card)){
            suitWanted = "";
            return straightForward(player,card);

        } else if (!suitWanted.isEmpty() && !matchesSuitWanted(card)) {
            return false;

        }
        else if (card.number().equals("Joker")) {
            makingAPlayerPickUp(5);
            cardPlaced(player,card);
            return true;
        }
        else if (card.number().equals("A")) {
            if (!cardsToHandToPlayer.isEmpty()) {
                rejectCards();
            }
            cardPlaced(player,card);
            return true;
        } else if (card.number().equals("8")) {
            setSuitWanted(action.getString("arguments"));
            cardPlaced(player,card);
            nextPlayer();
            return true;
        }
        else if(card.number().equals(centreCard.number()) || card.suit().equals(centreCard.suit())) {
            return straightForward(player,card);
        }
        return false;
    }
    private boolean playerPicksUpCard(Player player){
        Card cardToPick = stockPile.pop();
        player.pickUpCard(cardToPick);
        if(isStockPileLow()){
            restockFromDiscardPile();
        }
        nextPlayer();
        return true;
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

    private boolean acceptCards(){
        for (Card card: cardsToHandToPlayer
             ) {
            currentPlayer.pickUpCard(card);
        }
        cardsToHandToPlayer.clear();

        if(isStockPileLow()){
            restockFromDiscardPile();
        }
        nextPlayer();
        return true;
    }

    public Card getCentreCard() {
        return centreCard;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }


    public void nextPlayer(){
        int indexOfCurrentPlayer = gamePlayers.indexOf(currentPlayer);
        if(indexOfCurrentPlayer == gamePlayers.size()-1){
            currentPlayer = gamePlayers.get(0);
        }else {
            currentPlayer = gamePlayers.get(indexOfCurrentPlayer + 1);
        }
    }

    private boolean isStockPileLow(){
        return stockPile.size() <= 10;
    }

    private void cardPlaced(Player player,Card card){
        discardPile.push(card);
        centreCard = discardPile.peek();
        player.placeCardDown(card);
    }

    private void restockFromDiscardPile(){
        centreCard = discardPile.pop();

        while (!discardPile.empty()){
            Card card = discardPile.pop();
            stockPile.add(card);
        }
        Collections.shuffle(stockPile);
    }

    public void setCentreCard(Card card){
        centreCard = card;
    }

    public boolean matchesSuitWanted(Card card){
        return  suitWanted.equals(card.suit());
    }

    public void setSuitWanted(String suit){
        suitWanted = suit;
    }

    private boolean straightForward(Player player,Card card){
        if (card.number().matches("\\d+") && card.number().equals("2")) {
            makingAPlayerPickUp(2);
        }
        else if (card.number().equals("J")) {
            reverseList();
        } else if (card.number().equals("7")) {
            skipTheNextPlayer();
        }
        else {
            nextPlayer();
        }
        cardPlaced(player,card);
        return true;
    }
    public void removePlayer(String name){

        for (Player player: gamePlayers
             ) {
            if (player.getPlayerName().equals(name)){
                for (Card card: player.getCardsInHand()
                ) {
                    stockPile.push(card);
                }
                gamePlayers.remove(player);
            }
        }
    }
}

import org.Dealer.Dealer;
import org.Players.Player;
import org.cards.Card;
import org.cards.DeckOfCards;
import org.cards.GamePlay;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class GamePlayTests {
    private Player player1;
    private Player player2;
    private Dealer dealer;
    private ArrayList<Player> players;

    @BeforeEach
    void gameSetUp(){
        DeckOfCards deckOfCards = new DeckOfCards();
        player1 = new Player("Iron");
        player2 = new Player("Man");
        dealer = new Dealer(deckOfCards);

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    @Test
    void playerDiscards(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        dealer.dealCards(players);

        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);

        // Save the initial discard pile
        Stack<Card> discardPileAtBeginning = new Stack<>();
        discardPileAtBeginning.addAll(gamePlay.getDiscardPile());

        // json request
        JSONObject request = generateJson("Iron","discard","Spades","King","");

        // execute the request in game
        gamePlay.play(request);

        // tests that the discard pile in the game has gone up by 1
        assertTrue(discardPileAtBeginning.size() < gamePlay.getDiscardPile().size());

        // ensure that the card at the centre of game is the card the player just played
        assertEquals(new Card("Spades","King"),gamePlay.getCentreCard());
    }

    @Test
    void playerPicksUpCard(){
        dealer.dealCards(players);

        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);

        Stack<Card> beginningStockPile = new Stack<>();
        beginningStockPile.addAll(gamePlay.getStockPile());

        // json request
        JSONObject request = generateJson("Iron","pick","","","");

        // execute the request in game
        gamePlay.play(request);

        // test that the game stockpile has decreased
        assertTrue(beginningStockPile.size() > gamePlay.getStockPile().size());
    }

    @Test
    void playerGivesNextPlayer2Cards(){
        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());

        ArrayList<Card> cardsInitial = new ArrayList<>(player2.getCardsInHand());

        JSONObject request1 = generateJson("Iron","discard","Hearts","2","");

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);
        gamePlay.setCentreCard(new Card("Hearts","7"));

        gamePlay.play(request1);

        JSONObject request2 = generateJson("Man","accept","","","");

        gamePlay.play(request2);
        // check that player2 has 2 more cards than before
        assertTrue(player2.getCardsInHand().size() > cardsInitial.size());
        assertEquals(2,player2.getCardsInHand().size() -  cardsInitial.size());
    }

    @Test
    void playerGivesNextPlayer2CardsReject(){
        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());

        ArrayList<Card> cardsInitial = new ArrayList<>(player2.getCardsInHand());

        JSONObject request1 = generateJson("Iron","discard","Hearts","2","");

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);

        gamePlay.play(request1);

        JSONObject request2 = generateJson("Man","Discard","Spades","A","");

        gamePlay.play(request2);
        // check that player2 has the same cards as before
        assertEquals(player2.getCardsInHand().size(), cardsInitial.size());
        assertEquals(0,player2.getCardsInHand().size() -  cardsInitial.size());
    }
    @Test
    void playerReversesOrder(){


        ArrayList<Player> playersOrderInitially = new ArrayList<>(players);

        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());


        JSONObject request1 = generateJson("Iron","discard","Hearts","J","");

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);
        gamePlay.setCentreCard(new Card("Hearts","7"));
        gamePlay.play(request1);

        // ensure that the order has been reversed by checking that the first player has changed
        assertNotEquals(playersOrderInitially.get(0).getPlayerName(),gamePlay.getGamePlayers().get(0).getPlayerName());

        // ensure that the initial first player is now the last player
        assertEquals(playersOrderInitially.get(0).getPlayerName(),gamePlay.getGamePlayers().get(1).getPlayerName());
    }

    @Test
    void skipTheNextPlayer(){
        Player player3 = new Player("Kazekage");
        players.add(player3);

        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());


        JSONObject request1 = generateJson("Iron","discard","Clubs","7","");

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);
        gamePlay.setCentreCard(new Card("Clubs","8"));
        gamePlay.play(request1);

        assertEquals("Kazekage",gamePlay.getCurrentPlayer().getPlayerName());
    }

    @Test
    void playerChangesSuit(){

        // player one using an 8 to request a change of suit to Spades
        JSONObject request1 = generateJson("Iron","discard","Hearts","8","Spades");

        Card centreCard = dealer.setCentreCard();

        Stack<Card> stockPileBeginning = new Stack<>();
        stockPileBeginning.addAll(dealer.getDeckOfCards());

        GamePlay gamePlay = new GamePlay(players,stockPileBeginning,centreCard);

        gamePlay.setCentreCard(new Card("Hearts","7"));

        gamePlay.play(request1);

        // player 2 plays a Clubs card instead
        JSONObject request2 = generateJson("Man","discard","Clubs","3","");

        assertFalse(gamePlay.play(request2)); // play returns false
    }

    JSONObject generateJson(String name, String action, String suit,String number,String arguments){
        JSONObject request = new JSONObject();
        JSONObject card = new JSONObject();
        request.put("name",name);
        request.put("action",action);

        card.put("suit",suit);
        card.put("number",number);

        request.put("card",card);
        request.put("arguments",arguments);

        return request;
    }
}

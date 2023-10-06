package org;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.Dealer.Dealer;
import org.Players.Player;
import org.cards.Card;
import org.cards.DeckOfCards;
import org.cards.GamePlay;
import org.json.JSONObject;


import static j2html.TagCreator.article;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;

public class WebSocket {

    private static final Map<WsContext, Player> userPlayerMap = new ConcurrentHashMap<>();
    private static GamePlay gamePlay;
    private static final DeckOfCards cards = new DeckOfCards();

    private static final Dealer dealer = new Dealer(cards);
    private static Scanner scanner = new Scanner(System.in);
    private static Card centreCard;
    private static int numberOfPlayers;
    
    // private static int nextUserNumber = 1; // Assign to username for next connecting user

    public static void main(String[] args) {
        System.out.print("How many players: ");
        numberOfPlayers = scanner.nextInt();
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        }).start(7070);

        app.ws("/crazyeight", ws -> {
            ws.onConnect(ctx -> {

            });
            ws.onClose(ctx -> {

             
  
            });
            ws.onMessage(ctx -> {
                JSONObject request = new JSONObject(ctx.message());
                if(request.getString("command").equals("join")){
                    dealer.shuffleCards();

                    if(userPlayerMap.size() < numberOfPlayers ) {
                        String name = request.getString("name");
                        Player player = new Player(name);


                        userPlayerMap.put(ctx, player);
                        dealer.dealCards(List.of(player));
                        JSONObject deal = new JSONObject();


                        deal.put("message", "gameplay");
                        deal.put("cards", player.getCardsInHand());

                        ctx.send(Map.of(
                                        "messageType", "waitingPlayers",
                                        "message", "Waiting for " + String.valueOf(numberOfPlayers - userPlayerMap.size()) + " to join"
                                )

//                        ctx.send(Map.of(
//                                        "messageType", "gameplay",
//                                        "cards", player.getCardsInHand(),
//                                        "centreCard", centreCard
//                                )
                        );
                    }

                    if(userPlayerMap.size() == numberOfPlayers){
                        dealer.shuffleCards();
                        centreCard = dealer.setCentreCard();
                        gamePlay = new GamePlay(new ArrayList<>(userPlayerMap.values()),dealer.getDeckOfCards(),centreCard);

                        for (WsContext context : userPlayerMap.keySet()) {
                            context.send(Map.of(
                                            "messageType", "gameplay",
                                            "cards", userPlayerMap.get(context).getCardsInHand(),
                                            "centreCard", gamePlay.getCentreCard()
                                    )
                            );
                        }
                    }
                }
                if(request.getString("command").equals("gameplay")) {

                    String playerName = userPlayerMap.get(ctx).getPlayerName();
                   

                    request.put("name", playerName);
                    gamePlay.play(request);
                
                    JSONObject deal = new JSONObject();


                    deal.put("message", "gameplay");
                    deal.put("cards", userPlayerMap.get(ctx).getCardsInHand());

                    for (WsContext context : userPlayerMap.keySet()) {
                        context.send(Map.of(
                                        "messageType", "gameplay",
                                        "cards", userPlayerMap.get(context).getCardsInHand(),
                                        "centreCard", gamePlay.getCentreCard()
                                )
                        );
                    }
                    
                }

            });
        });
    }

    // Sends a message from one user to all users, along with a list of current usernames
    // private static void broadcastMessage(String sender, String message) {
    //     userUsernameMap.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
    //         session.send(
    //                 Map.of(
    //                         "userMessage", createHtmlMessageFromSender(sender, message),
    //                         "userlist", userUsernameMap.values()
    //                 )
    //         );
    //     });
    // }

    // Builds a HTML element with a sender-name, a message, and a timestamp
    private static String createHtmlMessageFromSender(String sender, String message) {
        return article(
                b(sender + " says:"),
                span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
                p(message)
        ).render();
    }
}

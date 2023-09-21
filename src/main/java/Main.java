import org.cards.Dealer.Dealer;
import org.cards.DeckOfCards;

public class Main {
    public static void main(String[] args) {

        DeckOfCards deckOfCards = new DeckOfCards();
        System.out.println(deckOfCards.getDeckOfCards());

        Dealer dealer = new Dealer(deckOfCards);

        dealer.shuffleCards();

        System.out.println(dealer.getDeckOfCards());
    }
}

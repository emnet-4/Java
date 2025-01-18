import java.util.Random;

public class Deck {

    private Card[] cards;
    private int top;
     // Index of the top card in the deck

    public Deck() {
        cards = new Card[52];
        top = 0;
        int index = 0;
        for (int suit = 1; suit <= 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                cards[index++] = new Card(suit, rank);
            }
        }
    }

    public void shuffle() {
        // Shuffle the deck 
        Random rand = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
        top = 0; // Reset the top card index
    }

    public Card deal() {
        if (top < cards.length) {
            return cards[top++];
        }
        return null; // No cards left to deal
    }
}

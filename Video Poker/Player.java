import java.util.ArrayList;

public class Player {

    private ArrayList<Card> hand;
    private double bankroll;
    private double bet;

    public Player() {
        hand = new ArrayList<>();
        bankroll = 50.0; 
        // Updated starting bankroll to 50 tokens
    }

    public void addCard(Card c) {
        hand.add(c);
    }

    public void removeCard(Card c) {
        hand.remove(c);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void placeBet(double amt) {
        if (amt > bankroll) {
            throw new IllegalArgumentException("Bet exceeds current bankroll!");
        }
        bet = amt;
        bankroll -= amt;
    }

    public void winnings(double odds) {
        bankroll += bet * odds;
    }

    public double getBankroll() {
        return bankroll;
    }

    public void resetHand() {
        hand.clear();
    }
}
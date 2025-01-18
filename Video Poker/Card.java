public class Card implements Comparable<Card> {

    private int suit; 
    // 1=Clubs, 2=Diamonds, 3=Hearts, 4=Spades
    private int rank; 
    // 1=Ace, 2-10, 11=Jack, 12=Queen, 13=King

    public Card(int s, int r) {
        suit = s;
        rank = r;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public int compareTo(Card c) {
        // Compare cards by rank (then by suit if necessary)
        if (this.rank == c.rank) {
            return this.suit - c.suit;
        }
        return this.rank - c.rank;
    }

    @Override
    public String toString() {
        String[] suits = {"c", "d", "h", "s"};
        String[] ranks = {"A", "2", "3", "4", "5", "6",
         "7", "8", "9", "10", "J", "Q", "K"};
        return suits[suit - 1] + ranks[rank - 1];
    }
}
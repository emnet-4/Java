import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    private Player player;
    private Deck cards;
    private boolean isTestMode;
    private ArrayList<Card> testHand;

    public Game(String[] testHand) {
        this();
        isTestMode = true;
        this.testHand = new ArrayList<>();
        for (String cardStr : testHand) {
            int suit = "cdhs".indexOf(cardStr.charAt(0)) + 1; 
            // Get suit from first character
            String rankStr = cardStr.substring(1); 
            // Get the rank part (rest of the string)
            int rank;

            // Map rank strings to integers
            switch (rankStr) {
                case "A": rank = 1; break; // Ace
                case "J": rank = 11; break; // Jack
                case "Q": rank = 12; break; // Queen
                case "K": rank = 13; break; // King
                default: rank = Integer.parseInt(rankStr); // Numbers 2-10
            }

            this.testHand.add(new Card(suit, rank));
        }
    }

    public Game() {
        player = new Player();
        cards = new Deck();
        cards.shuffle();
        isTestMode = false;
    }

    public void play() {
        System.out.println("Welcome to Video Poker!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Your current bankroll: " + player.getBankroll());
            System.out.print("Enter bet (or 0 to quit): ");
            double bet = scanner.nextDouble();

            if (bet == 0) break; // End game
            
            // Ensure the bet is valid
            if (bet < 1 || bet > player.getBankroll()) {
                System.out.println("Please bet between 1 and " + player.getBankroll() + ".");
                continue;
            }

            player.placeBet(bet);
            player.resetHand();

            // Dealing cards
            if (isTestMode) {
                player.getHand().addAll(testHand);
            } else {
                for (int i = 0; i < 5; i++) {
                    player.addCard(cards.deal());
                }
            }

            System.out.println("Your hand: " + player.getHand());
            System.out.print("To replace cards(1-5, space-separated, or 0 to keep all): ");
            scanner.nextLine(); // Consume newline
            String input = scanner.nextLine();
            if (!input.equals("0")) {
                String[] indices = input.split(" ");
                // Remove cards in reverse order to avoid shifting indices
                for (int i = indices.length - 1; i >= 0; i--) {
                    int idx = Integer.parseInt(indices[i]) - 1;
                    player.removeCard(player.getHand().get(idx));
                }
                for (int i = 0; i < indices.length; i++) {
                    player.addCard(cards.deal());
                }
            }

            System.out.println("Your final hand: " + player.getHand());
            String result = checkHand(player.getHand());
            System.out.println("Result: " + result);

            double payout = getPayout(result);
            player.winnings(payout);
            System.out.println("You won: " + (bet * payout) + " tokens");

            if (player.getBankroll() <= 0) {
                System.out.println("You are out of tokens. Game over.");
                break;
            }
        }
        scanner.close();
    }

public String checkHand(ArrayList<Card> hand) {
    Collections.sort(hand);

    boolean flush = true;
    boolean straight = true;
    int[] rankCounts = new int[14];
     // Array for counting ranks

    for (int i = 0; i < hand.size(); i++) {
        rankCounts[hand.get(i).getRank()]++;
        if (i > 0 && hand.get(i).getSuit() != hand.get(i - 1).getSuit()) {
            flush = false; // Not all suits are the same
        }
    }

    // Check for straight
    int consecutiveCount = 1; // Start with one card
    for (int i = 1; i < hand.size(); i++) {
        if (hand.get(i).getRank() == hand.get(i - 1).getRank() + 1) {
            consecutiveCount++;
        } else if (hand.get(i).getRank() != hand.get(i - 1).getRank()) {
            straight = false;
			 // If not consecutive, it's not a straight
            break; // Exit the loop
        }
    }

    // Check for the Ace-low straight (A, 2, 3, 4, 5)
    if (rankCounts[1] > 0 && rankCounts[2] > 0 && 
    rankCounts[3] > 0 && rankCounts[4] > 0 && rankCounts[5] > 0) {
        straight = true; 
		// A, 2, 3, 4, 5 is a valid straight
    }

    // Determine if it's a straight
    if (consecutiveCount == 5) {
        straight = true;
		 // Confirm if we have 5 consecutive cards
    } else {
        straight = false; // Reset if not
    }

    // Check for Royal Flush specifically
    if (flush && rankCounts[1] > 0 && rankCounts[10] > 0 
    && rankCounts[11] > 0 && rankCounts[12] > 0 && rankCounts[13] > 0) {
        return "Royal Flush"; // A, K, Q, J, 10 of the same suit
    }

    // Check for Straight Flush
    if (flush && straight) {
        return "Straight Flush";
		 // Any other straight flush
    }

    if (flush) return "Flush"; // Check for flush
    if (straight) return "Straight"; // Check for straight

    int pairs = 0, threes = 0, fours = 0;
    for (int count : rankCounts) {
        if (count == 2) pairs++;
        if (count == 3) threes++;
        if (count == 4) fours++;
    }

    if (fours == 1) return "Four of a Kind";
    if (threes == 1 && pairs == 1) return "Full House";
    if (threes == 1) return "Three of a Kind";
    if (pairs == 2) return "Two Pairs";
    if (pairs == 1) return "One Pair";

    return "No Pair"; // Default case
}

    private double getPayout(String result) {
        switch (result) {
            case "Royal Flush": return 250;
            case "Straight Flush": return 50;
            case "Four of a Kind": return 25;
            case "Full House": return 6;
            case "Flush": return 5;
            case "Straight": return 4;
            case "Three of a Kind": return 3;
            case "Two Pairs": return 2;
            case "One Pair": return 1;
            default: return 0;
        }
    }
}
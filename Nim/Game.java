import java.util.Random;

public class Game {
    
    private int marbles;
    private Human humanPlayer;
    private Computer computerPlayer;
    
    public Game(int difficulty){
        marbles = new Random().nextInt(91) + 10;// generating number between 10 -100
        humanPlayer = new Human();
        computerPlayer = new Computer(difficulty);   
    }
    
    public void play() {
        System.out.println("The game starts with " + marbles + " marbles.");

        Random random = new Random();
        boolean isHumanTurn = random.nextBoolean();// randomly deciding who starts

        while (marbles > 0) {
            if (isHumanTurn) {
                // Human player's turn
                System.out.println("Human's turn:");
                humanPlayer.move(marbles);
                int humanChoice = humanPlayer.getChoice();

                marbles -= humanChoice;
                System.out.println("Human took " + humanChoice + " marbles. " + marbles + " marbles left.");

                if (marbles == 1) {
                    declareWinner(true); // Human wins
                    break;
                }
            } else {
                // Computer player's turn
                System.out.println("Computer's turn:");
                computerPlayer.move(marbles);
                int computerChoice = computerPlayer.getChoice();

                marbles -= computerChoice;
                System.out.println("Computer took " + computerChoice + " marbles. " + marbles + " marbles left.");

                if (marbles == 1) {
                    declareWinner(false); // Computer wins
                    break;
                }
            }

            isHumanTurn = !isHumanTurn; // Switch turns
        }
    }

    private void declareWinner(boolean isHumanWinner) {// method to declare the winner
        if (isHumanWinner) {
            System.out.println("Human wins!");
        } else {
            System.out.println("Computer wins!");
        }
    }
} 
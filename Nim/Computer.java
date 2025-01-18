import java.util.Random;

public class Computer{
    
    private int mode;
    private int choice;
    
    public Computer(int m){
        mode = m;
        choice = -1;
    }
    
    public void move(int marblesLeft){

        if (mode == 1) { // Stupid mode
            choice = new Random().nextInt(Math.min(marblesLeft / 2, marblesLeft)) + 1;
        } else { // Smart mode
            boolean isLosingPosition = false;
            for (int n = 0; n < Integer.SIZE; n++) {
                if (marblesLeft == (1 << n) - 1) { // Check if marblesLeft is 2^n - 1
                    isLosingPosition = true;
                    break;
                }
            }

            if (isLosingPosition) {
                //if the player makes the smart move
                choice = new Random().nextInt(Math.min(marblesLeft / 2, marblesLeft)) + 1;
            } else {
                //if the player didn't leave the pile 2*n-1
                int powerOfTwo = 1;
                while (powerOfTwo <= marblesLeft) {
                    powerOfTwo *= 2;
                }
                powerOfTwo /= 2;
                choice = marblesLeft - powerOfTwo + 1; // Optimal move
            }
        }
    }    

    public int getChoice() {
        return choice;
    }
    
    
}
/*****************************************
 * Emnet Tsegaye
 * eht2135
 * Human Nim player 
 ****************************************/ 
import java.util.Scanner;

public class Human{
   
    private int choice;
    private Scanner input;
    
    public Human(){
        input=new Scanner(System.in);
        choice = -1;
    }
    
    public void move(int marblesLeft){
    
        // Setting up the range of choice from 1 to half the marbles
    System.out.println("Your turn. There are " + marblesLeft + " marbles left."); 
    do { System.out.print("How many marbles do you want to take (1 to " + marblesLeft / 2 + ")? "); 
    choice = input.nextInt();
    } 
     while (choice < 1 || choice > marblesLeft / 2);
      // if picked a value outside of the range the loop repeats 
    
    }
    
    public int getChoice(){
        return choice;
    }
    
    
}
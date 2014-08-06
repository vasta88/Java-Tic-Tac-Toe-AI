package tictactoe;

/**
 * @author Marissa Solomon, 2014
 */
 
import java.util.*;
import java.io.*;

public class TicTacToe {
    public CellVal[][] board = new CellVal[3][3];
    private CellVal turn = CellVal.EMPTY;
    private TermState endState = TermState.UNKNOWN;
    private int move = -1;
    
    TicTacToe() {
        int rand = (int)(Math.random() * 2);
        if (rand == 0)
            turn = CellVal.X;
        else
            turn = CellVal.O;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                board[i][j] = CellVal.EMPTY;
        }
    }
    
    void displayMenu(){
        System.out.println("\t\tWelcome to Tic Tac Toe");
        System.out.println("You are X and the computer is O, - is a free space");
        System.out.println("Board Spaces are numbered 1-9 as follows:");
        System.out.print("\t\t1 2 3\n\t\t4 5 6\n\t\t7 8 9\n");
        System.out.println("When prompted enter the number for where you want to move");
        System.out.println("");
    }
    
    int getUserMove(){
        boolean valid = false;
        Scanner in = new Scanner(System.in);
        while(!valid) {
            System.out.print("Enter your move: ");
            this.move = in.nextInt();
            this.move--;  //to account for 0 start in array
            if (move < 0 || move > 8)
                System.out.println("You can only move to spots 1-9");
            else if (this.board[move/3][move%3] != CellVal.EMPTY)
                System.out.println("That space is taken. Try again");
            else
                valid = true;
        }
        return move;
    }
    
    int getCompMove() {
        System.out.println("Let me think...");
        TicTacNode node = new TicTacNode(this.board);
        node = node.findMax();
        return node.move; 
    }
    
    void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.print("\t\t");
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] == CellVal.X) 
                    System.out.print("X ");
                else if (this.board[i][j] == CellVal.O)
                    System.out.print("O ");
                else
                    System.out.print("- ");
            }
            System.out.print("\n");
        }
    }
    
    void printConclusion(TermState s) {
        if (s == TermState.WIN)
            System.out.println("Ha, Ha! I win.");
        else if (s == TermState.LOSE)
            System.out.println("Oh, no! You have defeated me.");
        else
            System.out.println("It's a draw! Good Game.");
    }
    
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.displayMenu();
        if (game.turn == CellVal.O)
            System.out.print("Computer has");
        else
            System.out.print("You have");
        System.out.print(" been chosen to go first\n");
        
        while (game.endState == TermState.UNKNOWN) {
            int move;
            
            //user turn
            if (game.turn == CellVal.X) {
                move = game.getUserMove();  
                game.board[move/3][move%3] = game.turn;
                game.turn = CellVal.O; //switch turns
            }
            
            //computer's turn
            else {
                move = game.getCompMove();
                System.out.println("Move is: "+move);
                game.board[move/3][move%3] = game.turn;
                game.turn = CellVal.X; //switch turns
            }
            
            game.printBoard();
            TicTacNode now = new TicTacNode(game.board);
            game.endState = now.terminalTest(game.board);
        } 
        
        //game is over
        game.printConclusion(game.endState);
        System.out.println("Thanks for playing. Goodbye!");
    }
    
}

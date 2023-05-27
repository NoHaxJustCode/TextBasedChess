package Chess;

import Board.*;
//import Controller.*;

import java.util.Scanner;

/**
 * @authors Avinash Paluri and Vishal Patel
 *
 * Class that creates and maintains the chess game
 */

public class Chess {

    /**
	 * Creates the board and scanner and allows the player to play the game until it is over/forfeited
     * @param args the string array of args being passed in. Contains valid string moves
	 */
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        Scanner scanner = new Scanner(System.in);
        boolean drawInitiated = false;
        boolean mustDraw = false;
        boolean tryAgain = false;

        while (true) {
            if(!tryAgain)
            {
                System.out.println(board.toString()+"\n");
                tryAgain=false;
            }

            if (board.isCheckmate(board.getCurrentPlayer())) {
                System.out.println("Checkmate!\n" + board.getOtherPlayer() + " wins!");
                scanner.close();
                return;
            } else if (board.isCheck(board.getCurrentPlayer())) {
                System.out.println("Check!");
                tryAgain=false;
            }

            System.out.print(board.getCurrentPlayer() + "'s move: ");
            String input = scanner.nextLine();
            String[] tokens = input.split(" ");
            
            // Check for resignation
            if (tokens.length == 1 && tokens[0].equalsIgnoreCase("resign")) {
                System.out.println(board.getOtherPlayer() +" wins!");
                scanner.close();
                return;
            }
            
            // Check for draw offer
            if (tokens.length > 2 && tokens[2].equalsIgnoreCase("draw?")) {
                board.setDrawOffered(true, board.getCurrentPlayer());
                drawInitiated = true;
                tryAgain=false;
            }
            
            // Check for draw acceptance
            if (tokens.length == 1 && tokens[0].equalsIgnoreCase("draw") && board.isDrawOffered(board.getCurrentPlayer())) {
                scanner.close();
                return;
            }
            try{
                int startCol = (int)tokens[0].charAt(0) - 97;
                int startRow = Integer.parseInt(tokens[0].substring(1)) - 1;
                int endCol = (int)tokens[1].charAt(0) - 97;
                int endRow = Integer.parseInt(tokens[1].substring(1)) - 1; 

                if (mustDraw || !board.movePiece(startRow, startCol, endRow, endCol)) {
                    System.out.println("Illegal move! Try again.\n");   
                    tryAgain=true;            
                } else if(drawInitiated){
                    board.promotePiece(endRow, endCol, tokens);
                    board.nextPlayer();
                    mustDraw = true;
                    tryAgain=false;
                } else {
                    board.promotePiece(endRow, endCol, tokens);
                    board.nextPlayer();   
                    tryAgain=false;                 
                }
            }catch(Exception e)
            {
                System.out.println("Illegal move! Try again.\n");
                tryAgain = true;               
            }
        }
    }
}
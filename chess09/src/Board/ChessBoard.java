package Board;
import java.util.ArrayList;
import java.util.List;

import Controller.*;

/**
 * @authors Avinash Paluri and Vishal Patel
 *
 * Class that creates and maintains the chess board
 */

public class ChessBoard {
    private final int BOARD_SIZE = 8;
    private ChessPiece[][] board;
    private PieceColor currentPlayer;
    private boolean drawOfferedWhite;
    private boolean drawOfferedBlack;

    /**
     * constructor for the chess game. Initializes the board with the default size of 8,
     * sets the first player as white, calls initializeBoard() to set all the pieces to the board
     * 
     * @see initializeBoard()
     */
    public ChessBoard() {
        board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PieceColor.WHITE;
        initializeBoard();
    }
    /**
	 * Creates a 2D array and fills it with all the pieces in the correct positions
	 */
    public void initializeBoard() {
        // Initialize white
        board[0][0] = new Rook(0, 0, PieceColor.WHITE);
        board[0][1] = new Knight(0, 1, PieceColor.WHITE);
        board[0][2] = new Bishop(0, 2, PieceColor.WHITE);
        board[0][3] = new Queen(0, 3, PieceColor.WHITE);
        board[0][4] = new King(0, 4, PieceColor.WHITE);
        board[0][5] = new Bishop(0, 5, PieceColor.WHITE);
        board[0][6] = new Knight(0, 6, PieceColor.WHITE);
        board[0][7] = new Rook(0, 7, PieceColor.WHITE);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(1, i, PieceColor.WHITE);
        }
    
        // Initialize black
        board[7][0] = new Rook(7, 0, PieceColor.BLACK);
        board[7][1] = new Knight(7, 1, PieceColor.BLACK);
        board[7][2] = new Bishop(7, 2, PieceColor.BLACK);
        board[7][3] = new Queen(7, 3, PieceColor.BLACK);
        board[7][4] = new King(7, 4, PieceColor.BLACK);
        board[7][5] = new Bishop(7, 5, PieceColor.BLACK);
        board[7][6] = new Knight(7, 6, PieceColor.BLACK);
        board[7][7] = new Rook(7, 7, PieceColor.BLACK);

        board[6][1] = new Knight(6, 1, PieceColor.BLACK);
        board[6][2] = new Bishop(6, 2, PieceColor.BLACK);
        board[6][3] = new Queen(6, 3, PieceColor.BLACK);
        board[6][5] = new Bishop(6, 5, PieceColor.BLACK);
        board[6][6] = new Knight(6, 6, PieceColor.BLACK);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(6, i, PieceColor.BLACK);
        }
    
        // Fill in empty spaces
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    
        // Initialize other variables
        currentPlayer = PieceColor.WHITE;
    }
    
    /**
	 * Checks if the King of the given piece color is in check.
     * @param color     the color of the chess piece
	 * @return      returns if there is no possible move that can save the king
	 */
    public boolean isCheck(PieceColor color) {
        // Find the king of the given color
        ChessPiece king = findKing(color);
        if (king == null) {
            // Shouldn't happen if the game is set up correctly
            return false;
        }
        // Check if any enemy piece can attack the king
        List<ChessPiece> pieces = getPieces(getOtherPlayer());
        for (ChessPiece piece : pieces) {
            if (piece.isValidMove(king.getRow(), king.getCol(), board)) {
                return true;
            }
        }
        // The king is not in check
        return false;
    }
    /**
	 * Checks if the King of the given piece color is in checkmate
     * @param color     the color of the chess piece
	 * @return          returns true if there is piece of the opposite color that can capture the king. False otherwise
	 */
    public boolean isCheckmate(PieceColor color) {
        if (!isCheck(color)) {
            // If the king is not in check, then it's not checkmate
            return false;
        }
    
        // Check if any piece can block the check or capture the threatening piece
        List<ChessPiece> pieces = getPieces(color);
        for (ChessPiece piece : pieces) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (piece.isValidMove(row, col, board)) {
                        // Try the move and see if it gets the king out of check
                        int oR = piece.getRow();
                        int oC = piece.getCol();
                        ChessPiece capturedPiece = board[row][col];
                        piece.setRow(row);
                        piece.setCol(col);
                        board[row][col] = piece;
                        boolean kingStillInCheck = isCheck(color);
                        piece.setRow(oR);
                        piece.setCol(oC);
                        board[oR][oC] = piece;
                        board[row][col] = capturedPiece;
                        if (piece != null && !kingStillInCheck) {
                            // The piece can block the check or capture the threatening piece, so it's not checkmate
                            return false;
                        }
                    }
                }
            }
        }
        // If no piece can move to prevent checkmate, then it's checkmate
        return true;
    }
    
    /**
     * finds the king of the given chess piece color on the board
     * @param color     the color of the chess piece
	 * @return          returns the king piece (object) by searching through the normal board based on the color
	 */
    public King findKing(PieceColor color) {
        // Loop through the board to find the king
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor() == color) {
                    // Found the king
                    return (King) piece;
                }
            }
        }
        // King not found
        return null;
    }

    /**
	 * gets all the pieces of the given color on th board.
     * @param color     the color of the chess piece
	 * @return          returns all the pieces of a color that are still alive on the board as List of ChessPieces
	 */
    public List<ChessPiece> getPieces(PieceColor color) {
        List<ChessPiece> pieces = new ArrayList<>();
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }
    
        return pieces;
    }

    /**
	 * Gets a copy of the chessboard
	 * @return      Returns a copy of the current board's game state as a 2D array
	 */
    public ChessPiece[][] getBoardCopy() {
        ChessPiece[][] copy = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null) {
                    copy[row][col] = piece;
                }
            }
        }
        return copy;
    }
    
    /**
	 * Determines if the given piece has a check
     * @param color        the color of the chess piece
     * @param board        the current state of the board
	 * @return             returns true if the board has a check for the color passed through, false otherwise
	 */
    public boolean isCheck(PieceColor color, ChessPiece[][] board) {
        // Find the king of the given color
        ChessPiece king = findKing(color, board);
        if (king == null) {
            // Shouldn't happen if the game is set up correctly
            return false;
        }
        
        // Check if any enemy piece can attack the king
        List<ChessPiece> pieces = getPieces(getOtherPlayer());
        for (ChessPiece piece : pieces) {
            if (piece.isValidMove(king.getRow(), king.getCol(), board)) {
                return true;
            }
        }
        
        // The king is not in check
        return false;
    }

    /**
     * finds the king of the given chess piece color on the board
     * @param color     the color of the chess piece
     * @param board     the current state of the board
	 * @return          Returns the king piece by searching through the passed in board based on the color
	 */
    public ChessPiece findKing(PieceColor color, ChessPiece[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessPiece piece = board[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    return piece;
                }
            }
        }
        // Should never happen if the board is set up correctly
        return null;
    }
    
    /**
     * This method checks to see if a square is being attacked. It is used in conjunction with the movePiece() method.
     * This is primarily used to check if the king's pathway will be attacked during castling.
     * 
     * @param kingRow   the row that the King piece is in as an int
     * @param toCol     the column that needs to be checked with respect to the king row
     * @return          returns true if an opponent piece can attack a square. returns false if no piece can attack the square
     */
    public boolean isAttacked(int kingRow, int toCol){
        List<ChessPiece> pieces = getPieces(getOtherPlayer());
        for(ChessPiece piece : pieces){
            if(piece.isValidMove(kingRow, toCol, board)){
                return true;
            }
        }
        return false;
    }
 
    /**
	 * movePiece()
     * @param fromRow   the row the chess piece originates from
     * @param fromCol   the column the chess piece originates from
     * @param toRow     the desired row to move the chess piece to
     * @param toCol     the desired column to move the chess piece to
	 * @return          Returns true if the move indicated by the player can actually be done and moves the piece if possible. False otherwise.
	 */
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        ChessPiece piece = board[fromRow][fromCol];
        if(piece == null)
            return false;
        
        if(piece.getColor() != getCurrentPlayer())
            return false;
        
        //Same spot
        if(toRow==fromRow && toCol==fromCol)
        {
            return false;
        }
        
        //Out of bounds
        if(toRow < 0 || toRow>=8 || toCol < 0 || toCol >=8)
        {
            return false;
        }
        // Check if the move is legal
        if (!piece.isValidMove(toRow, toCol, board)) {
            return false;
        }

        //check if the move is a castle move
        if(piece instanceof King && Math.abs(toCol - fromCol) == 2){
            King king = (King) piece;
            //kingside castling
            if(toCol > fromCol ){
                ChessPiece rook = board[fromRow][7];
                //if castling is true, then it is reflected on the board
                if(king.canCastle(fromRow, fromCol, rook, board)){
                    //if a square is attackable by opponent piece, return false
                    for(int attackCol = king.getCol() + 1; attackCol < rook.getCol(); attackCol++){
                        if(isAttacked(king.getRow(), attackCol)){
                            return false;
                        }
                    }
                }
                // updating king's position
                king.setCol(toCol);
                board[toRow][toCol] = king;
                board[fromRow][fromCol] = null;

                // updating rook's position
                rook.setCol(toCol - 1);
                board[toRow][toCol - 1] = rook;
                board[fromRow][7] = null;
            }   //end of kingside castling
            else{ //queenside castling
                ChessPiece rook = board[fromRow][0];
                if(king.canCastle(fromRow, fromCol, rook, board)) {
                    for(int attackCol = king.getCol() -1; attackCol > rook.getCol(); attackCol--){
                        if(isAttacked(king.getRow(), attackCol)){
                            return false;
                        }
                    }
                }
                // updating king's position
                king.setCol(toCol);
                board[toRow][toCol] = king;
                board[fromRow][fromCol] = null;

                // updating rook's position
                rook.setCol(toCol + 1);
                board[toRow][toCol + 1] = rook;
                board[fromRow][0] = null;
            }   //end of queenside castling
        }   //end of castling check statement
        
        //Temporarily make the move and check for checks
        ChessPiece capturedPiece = board[toRow][toCol];
        piece.setRow(toRow);
        piece.setCol(toCol);
        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;
        boolean kingInCheck = isCheck(getCurrentPlayer());
        
        //Undo the move
        piece.setRow(fromRow);
        piece.setCol(fromCol);
        board[fromRow][fromCol] = piece;
        board[toRow][toCol] = capturedPiece;
    
        if (kingInCheck) {
            return false;
        }
    
        //Remove original piece if captureable
        if(piece.canCapturePiece(toRow, toCol, board))
        {
            board[toRow][toCol].setCaptured(true);
            board[toRow][toCol] = null;
        }
    
        // Move the piece
        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;
    
        // Update the piece's position
        piece.setRow(toRow);
        piece.setCol(toCol);
    
        return true;
    }    

    /**
	 * Returns the string of the chess board with all the pieces in their respective spots.
	 * @return Returns the string of the chess board with all the pieces in their respective spots. 
	 */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    if ((row + col) % 2 == 0) {
                        sb.append("   ");
                    } else {
                        sb.append("## ");
                    }
                } else {
                    sb.append(board[row][col].getSymbol()).append(" ");
                }
            }
            sb.append(row + 1).append("\n");
        }
        sb.append(" a  b  c  d  e  f  g  h");
        return sb.toString();
    }
    
    /**
	 * Get the current player
	 * @return Returns the color of the player
	 */
    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    /**
	 * Get the opposing player
	 * @return  Returns the color of the opponent
	 */
    public PieceColor getOtherPlayer() {
        if(currentPlayer.equals(PieceColor.BLACK))
            return PieceColor.WHITE;
        return PieceColor.BLACK;
    }

    /**
	 * Sets the indicator that the opponent wants to draw
     * @param offered   This should be set to true as a draw is offered.
     * @param color     The color of the player that wants to draw
	 */
    public void setDrawOffered(boolean offered, PieceColor color)
    {
        if(color == PieceColor.WHITE)
            this.drawOfferedWhite = offered;
        else
            this.drawOfferedBlack = offered;
    }

    /**
	 * Gets the status of if a draw has been offered. This is used so that the opponent of the player
     * who has requested to draw has to draw and therefore end the game.
     * @param color     the color of the player that needs to be checked for the draw
	 * @return          Returns if draw is mutually agreed upon
	 */
    public boolean isDrawOffered(PieceColor color)
    {
        if(color == PieceColor.WHITE)
            return this.drawOfferedBlack;
        else
            return this.drawOfferedWhite;
    }

    /**
	 * Gets and sets the turn to the next player. Used when a valid move or offer is executed.
	 */
    public void nextPlayer()
    {
        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
    }

    /**
     * Checks if piece being moved is a pawn and is at one of the ends of the board, if so
     * the piece is promoted to Q automatically unless specified by the user
     * @param endRow        the end row that the pawn should be at in order to be promoted
     * @param endCol        the end col that the pawn should be at in order to be promoted
     * @param tokens        
	 */
    public void promotePiece(int endRow, int endCol, String[] tokens)
    {
        if(board[endRow][endCol] instanceof Pawn == false)
        {
            return;
        }
        if(currentPlayer == PieceColor.BLACK && endRow == 0 || currentPlayer == PieceColor.WHITE && endRow == 7)
        {
            board[endRow][endCol] = null;
            if(tokens.length == 2)
            {
                board[endRow][endCol] = new Queen(endRow, endCol, currentPlayer);
                return;                  
            }
            switch(tokens[2].toUpperCase())
            {
                case "Q":
                    board[endRow][endCol] = new Queen(endRow, endCol, currentPlayer);
                    break;
                case "R":
                    board[endRow][endCol] = new Rook(endRow, endCol, currentPlayer);
                    break;
                case "B":
                    board[endRow][endCol] = new Bishop(endRow, endCol, currentPlayer);
                    break;
                case "N":
                    board[endRow][endCol] = new Knight(endRow, endCol, currentPlayer);
                    break;
                default:
                    board[endRow][endCol] = new Queen(endRow, endCol, currentPlayer);
                    return;                
            }
        }
    }
}
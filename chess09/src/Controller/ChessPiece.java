package Controller;

/**
 * @authors Avinash Paluri and Vishal Patel
 *
 * Abstract class that represents all the shared properties of a chess piece
 */

public abstract class ChessPiece {
    private int row;
    private int col;
    private PieceColor color;
    private boolean captured = false;
    private boolean moved;
    private boolean castle;
    
    /**
     * Sole constructor for this class. Creates the ChessPiece object and serves as
     * the blueprint for all the chess pieces in the game.
     * @param row       the row the piece needs to be instantiated at
     * @param col       the col the piece needs to be instantiated at
     * @param color     the color of the chess piece
     */
    public ChessPiece(int row, int col, PieceColor color) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.moved = false;
        this.castle = false;

    }

    /**
	 * Gets the row of the current chess piece
	 * @return Returns the current row of the chess piece
	 */
    public int getRow() {
        return row;
    }

    /**
     * Sets the current pieces row to the parameter
     * 
     * @param row   the row the chess piece needs to be set to
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
	 * Gets the column of the chess piece
	 * @return Returns the current col of the chess piece
	 */    
    public int getCol() {
        return col;
    }

    /**
     * Sets the current pieces col to the parameter
     * @param col   the col the chess piece needs to be set to
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Tracks to see if a chess piece has moved from its initial position. Primarily
     * used for the Kings, Rooks, and Pawns for their special moves (Castling, en passant)
     * 
     * @return returns true if the ChessPiece has moved, false otherwise
     */
    public boolean hasMoved() {
        return this.moved;
    }

    /**
     * changes the ChessPiece's moved status
     * 
     * @param value     true if the piece has moved and false if the piece has not moved.
     */
    public void setMoved(boolean value) {
        this.moved = value;
    }

    /**
     * checks to see if this piece has castled or not. Used for the Rook and King pieces.
     * @return returns true if the King/Rook has castled, otherwise false
     */
    public boolean hasCastled(){

        return this.castle;
    }

    /**
     * changes the King/Rook's castled status
     * @param value     true if the piece has castled, false otherwise.
     */
    public void setCastle(boolean value){
        this.castle = value;

    }

    /**
	 * Gets the color of this chess piece
	 * @return Returns the color of the chess piece
	 */      
    public PieceColor getColor() {
        return color;
    }

    /**
     * Checks to see if the piece is captured or not
     * @return Returns if the piece is captured
     */
    public boolean isCaptured(){
        return this.captured;
    }

    /**
     * sets the status of the piece as true or false
     * @param status    true means the piece is captured, false if it is not
     */
    public void setCaptured(boolean status){
        this.captured = status;
    }

    /**
     * Checks if the piece is a valid move for the given type of chess piece. It
     * checks all sorts of conditions unique to the piece such as castling, pawn promotion, etc.
     * 
     * @param toRow     the row the piece is being moved to
     * @param toCol     the column the piece is being moved to
     * @param board     the current board containing all the pieces on it
     * @return          Returns if the chosen position of the selected chess piece is legal
     */  
    public abstract boolean isValidMove(int toRow, int toCol, ChessPiece[][] board);

    /**
     * Checks to see if there is a piece at the given square on the board
     * @param row       the row the piece is being moved to
     * @param col       the column the piece is being moved to
     * @param board     the current board containing all the pieces on it
     * @return          Returns true if there is a piece at the given spot, otherwise false
     */
    public boolean hasPiece(int row, int col, ChessPiece[][] board){
        if(board[row][col] != null)
            return true;

        return false;
    }

    /**
     * Checks ot see if the given square is an opponent piece
     * 
     * @param row       the row the piece is being moved to
     * @param col       the column the piece is being moved to
     * @param board     the current board containing all the pieces on it
     * @return          returns true is the given coord has an opponent's piece, false otherwise
     */
    public boolean isOpponentPiece(int row, int col, ChessPiece[][] board){
        if (board[row][col].getColor() != getColor()){
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the current chess piece can capture the piece at the given square
     * @param toRow     the row the piece is being moved to, to capture the opponent piece
     * @param toCol     the column the piece is being moved to, to capture the opponent piece    
     * @return          Returns if the current chess piece can capture the piece on the given coords
     */
    public abstract boolean canCapturePiece(int toRow, int toCol, ChessPiece[][] board);

    /**
	 * Gets the string representation of the piece that the player utilizes when executing moves.
	 * @return Returns the indicator of the chess piece which can be mapped to its position on the chess board
	 */      
    public abstract String getSymbol();
}


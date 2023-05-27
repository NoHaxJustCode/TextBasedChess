package Controller;

public class Pawn extends ChessPiece{

    private Boolean firstMove;  /**@firstMove indicates whether the Pawn can make its first move. True = pawn can move 2 spaces, otherwise false.*/
    private Boolean enPassantVulnerable;

    /**
     * Constructor for the Pawn class
     * @param row
     * @param col
     * @param color
     */
    public Pawn(int row, int col, PieceColor color) {
        super(row, col, color);
        firstMove = true;
        enPassantVulnerable = false;
    }
    
    @Override
    public boolean isValidMove(int toRow, int toCol, ChessPiece[][] board) {
        int fromRow = this.getRow();
        int fromCol = this.getCol();
        ChessPiece targetPiece = board[toRow][toCol];
    
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);
    
        // Check if move is valid for the piece color
        if (this.getColor() == PieceColor.WHITE) {
            if (rowDiff == 1 && colDiff == 0 && targetPiece == null) {
                // Standard move one square forward
                this.firstMove = false;
                return true;
            } else if (firstMove && rowDiff == 2 && colDiff == 0 && targetPiece == null 
                       && board[fromRow+1][fromCol] == null) {
                // First move, can move two squares forward
                this.firstMove = false;
                this.enPassantVulnerable = true;
                return true;
            } else if (rowDiff == 1 && colDiff == 1 && targetPiece != null 
                       && targetPiece.getColor() == PieceColor.BLACK) {
                // Capture diagonally
                this.firstMove = false;
                return true;
            } else if(rowDiff == 1 && colDiff == 1 && targetPiece == null 
                       && board[fromRow][toCol] instanceof Pawn && ((Pawn)board[fromRow][toCol]).getEnPassant()
                       && board[fromRow][toCol].getColor() == PieceColor.BLACK){
                //capture the pawn with enPassant rule
                this.firstMove = false;
                board[fromRow][toCol] = null;   //removes opponent's pawn from the board
                return true;
            } else {
                // Invalid move
                return false;
            }
        } else { // PieceColor.BLACK
            if (rowDiff == -1 && colDiff == 0 && targetPiece == null) {
                // Standard move one square forward
                this.firstMove = false;
                return true;
            } else if (firstMove && rowDiff == -2 && colDiff == 0 && targetPiece == null 
                       && board[fromRow-1][fromCol] == null) {
                // First move, can move two squares forward
                this.firstMove = false;
                this.enPassantVulnerable = true;
                return true;
            } else if (rowDiff == -1 && colDiff == 1 && targetPiece != null 
                       && targetPiece.getColor() == PieceColor.WHITE) {
                // Capture diagonally
                this.firstMove = false;
                return true;
            } else if(rowDiff == -1 && colDiff == 1 && targetPiece == null 
                       && board[fromRow][toCol] instanceof Pawn && ((Pawn)board[fromRow][toCol]).getEnPassant()
                       && board[fromRow][toCol].getColor() == PieceColor.WHITE){
                //capture the pawn with enPassant rule
                this.firstMove = false;
                board[fromRow][toCol] = null;   //removes opponent's pawn from the board
                return true;
            } else {
                // Invalid move
                return false;
            }
        }
    }
    

    @Override
    public boolean canCapturePiece(int toRow, int toCol, ChessPiece[][] board) {
        if (isValidMove(toRow, toCol, board)) {
            ChessPiece targetPiece = board[toRow][toCol];
            // If the target piece is an opponent's piece, return true
            return targetPiece != null && targetPiece.getColor() != this.getColor();
        }
        return false;
    }

    /**
     * Gets the en passant value for this pawn piece
     * @return returns the value of enPassantVulnerable
     */
    public boolean getEnPassant(){
        return this.enPassantVulnerable;
    }

    /**
     * sets the status of enPassantVulnerable
     * 
     * @param status    true if the pawn is vulnerable to en passant, false otherwise
     */
    public void setEnPassant(boolean status){
        this.enPassantVulnerable = status;
    }
    
    @Override
    public String getSymbol() {
        if(getColor() == PieceColor.WHITE)
            return "wP";
        return "bP";
    }
}

package Controller;

public class Knight extends ChessPiece {

    /**
     * Constructor for the Knight class
     * @param row
     * @param col
     * @param color
     */
    public Knight(int row, int col, PieceColor color) {
        super(row, col, color);
    }
    
    @Override
    public boolean isValidMove(int toRow, int toCol, ChessPiece[][] board) {
        int rowDiff = Math.abs(toRow - getRow());
        int colDiff = Math.abs(toCol - getCol());
    
        if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)) {
            // The knight can make this L-shaped move
            ChessPiece destinationPiece = board[toRow][toCol];
            if (destinationPiece == null || destinationPiece.getColor() != getColor()) {
                // The destination square is either unoccupied or occupied by an opponent's piece
                return true;
            }
        }
    
        // The move is not valid
        return false;
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
    
    @Override
    public String getSymbol() {
        if(getColor() == PieceColor.WHITE)
            return "wN";
        return "bN";
    }
}


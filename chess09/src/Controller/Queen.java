package Controller;

public class Queen extends ChessPiece{
    
    public Queen(int row, int col, PieceColor color) {
        super(row, col, color);
    }
    @Override
    public boolean isValidMove(int toRow, int toCol, ChessPiece[][] board) {
        if (toRow == getRow() && toCol == getCol()) {
            // moving to the same position is not a valid move
            return false;
        }

        // check if the move is valid for a bishop or a rook
        Bishop bishop = new Bishop(getRow(), getCol(), getColor());
        Rook rook = new Rook(getRow(), getCol(), getColor());
        return bishop.isValidMove(toRow, toCol, board) || rook.isValidMove(toRow, toCol, board);
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
            return "wQ";
        return "bQ";
    }

}

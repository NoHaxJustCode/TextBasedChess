package Controller;

public class Rook extends ChessPiece{

    public Rook(int row, int col, PieceColor color) {
        super(row, col, color);
    }
    @Override
    public boolean isValidMove(int toRow, int toCol, ChessPiece[][] board) {
        // check if move is horizontal or vertical
        if(board[toRow][toCol] != null && board[toRow][toCol].getColor() == getColor())
        {
            return false;
        }

        if (getRow() == toRow || getCol() == toCol) {
            // check if there are any pieces in the way
            int start, end;
            if (getRow() == toRow) {
                start = Math.min(getCol(), toCol);
                end = Math.max(getCol(), toCol);
                for (int i = start + 1; i < end; i++) {
                    if (board[getRow()][i] != null) {
                        return false;
                    }
                }
            } else {
                start = Math.min(getRow(), toRow);
                end = Math.max(getRow(), toRow);
                for (int i = start + 1; i < end; i++) {
                    if (board[i][getCol()] != null) {
                        return false;
                    }
                }
            }
            // check if destination square is empty or has an opponent's piece
            if(board[toRow][toCol] == null || board[toRow][toCol].getColor() != getColor()){
                this.setMoved(true);      //if the rook moved, change this to true.
                return true;
            }
        }
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
            return "wR";
        return "bR";
    }
}

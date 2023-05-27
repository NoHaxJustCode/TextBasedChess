package Controller;

/**
 * @authors Avinash Paluri and Vishal Patel
 *
 * Class that represents the Bishop chess piece
 */

public class Bishop extends ChessPiece{

    /**
     * Constructor for the Bishop chesspiece that supers the parameters to the ChessPiece class constructor
     * @param row       row that the bishop belongs to
     * @param col       col that the bishop belongs to
     * @param color     color of the bishop piece
     */
    public Bishop(int row, int col, PieceColor color) {
        super(row, col, color);
    }
    
    @Override
    public boolean isValidMove(int toRow, int toCol, ChessPiece[][] board) {
        int currRow = getRow();
        int currCol = getCol();

        if( Math.abs(toRow - currRow) == 0 || Math.abs(toCol - currCol) == 0)
        {
            return false;
        }
        
        // check if move is diagonal
        if (Math.abs(currRow - toRow) != Math.abs(currCol - toCol)) {
            return false;
        }
        
        // check if path is clear
        int rowDir = (toRow - currRow) / Math.abs(toRow - currRow);
        int colDir = (toCol - currCol) / Math.abs(toCol - currCol);
        
        int nextRow = currRow + rowDir;
        int nextCol = currCol + colDir;
        
        while (nextRow != toRow || nextCol != toCol) {
            if (board[nextRow][nextCol] != null) {
                return false;
            }
            
            nextRow += rowDir;
            nextCol += colDir;
        }
        
        // check if destination is empty or has opponent's piece
        if (!hasPiece(toRow, toCol, board) || isOpponentPiece(toRow, toCol, board)) {
            return true;
        }
        // if (board[toRow][toCol] == null || board[toRow][toCol].getColor() != getColor().opposite()) {
        //     return true;
        // }
        
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
            return "wB";
        return "bB";
    }
}

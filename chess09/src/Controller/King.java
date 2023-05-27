package Controller;

public class King extends ChessPiece {

    /**
     * Constructor of the King class.
     * @param row
     * @param col
     * @param color
     */
    public King(int row, int col, PieceColor color) {
        super(row, col, color);
    }
    
    @Override
    public boolean isValidMove(int toRow, int toCol, ChessPiece[][] board) {
        // Check if the destination is within one square of the current position

        if(board[toRow][toCol] != null && !(board[toRow][toCol] instanceof Rook) && board[toRow][toCol].getColor() == getColor())
        {
            return false;
        }
        int rowDiff = Math.abs(toRow - this.getRow());
        int colDiff = Math.abs(toCol - this.getCol());
        if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 1)) {
            // The destination is within one square of the current position, so check if it's a valid move
            ChessPiece destPiece = board[toRow][toCol];
            if (destPiece == null || destPiece.getColor() != this.getColor()) {
                // The destination is empty or contains an opponent's piece, so it's a valid move
                this.setMoved(true);  //changes to true since the king is moving.
                return true;
            }
        }else if (rowDiff == 0 && colDiff == 2 && !this.hasMoved()){ //checking for castling
            ChessPiece rook;
            //determining Rook's position on the board
            if(toCol > this.getCol()){
                rook = board[this.getRow()][7];
            } else{
                rook = board[this.getRow()][0];
            }

            if(canCastle(this.getRow(), this.getCol(), rook, board)){
                this.setMoved(true);
                this.setCastle(true);
                rook.setMoved(true);
                rook.setCastle(true);
                return true;    //returns true if castling is Valid Move
            }
        }

        return false;
    }

    /**
     * Checks to see if a square is being attacked that the King will pass through or move to.
     * 
     * @param row
     * @param col
     * @param color
     * @param board
     * @return      returns true if an opponent piece can attack a square. Returns false if no piece can attack the square
     * 
     * @see canCastle()
     */
    public boolean isSquareAttacked(int row, int col, PieceColor color, ChessPiece[][] board) {
        // Check if any opposing piece can legally move to the square
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor() != this.getColor()) {
                    if (piece.isValidMove(row, col, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    /**
     * Checks to see if the King and Rook can castle.
     * 
     * @param kRow      the current row of the king
     * @param kCol      the current col of the king
     * @param rook      the rook that is to be castled with
     * @param board     the current state of the board
     * @return          returns true if the given rook and king can castle. False otherwise
     */
    public boolean canCastle(int kRow, int kCol, ChessPiece rook, ChessPiece[][] board) {
        if (rook == null) {
            return false;
        }
        int rCol = rook.getCol();
        // checking if either the king or rook have moved
        if (this.hasMoved() || rook.hasMoved()) {
            return false;
        }
    
        // setting the iteration variable to +/- 1 depending on king/queen side castle
        int kqCastle = kCol < rCol ? 1 : -1;
    
        // Check if the squares between the king and rook are clear
        for (int attackCol = kCol + kqCastle; attackCol < rCol; attackCol += kqCastle) {
            if (board[kRow][attackCol] != null) {
                // returns false if there is a piece between the king and rook
                return false;
            }
        }
    
        // Check if the king is in check or passes through an attacked square
        for (int col = kCol; col != rCol + kqCastle; col += kqCastle) {
            // Check if the square is attacked by an enemy piece
            if (isSquareAttacked(kRow, col, this.getColor().opposite(), board)) {
                return false;
            }
            // Check if the king passes through the attacked square
            if (col != kCol && isSquareAttacked(kRow, col, this.getColor().opposite(), board)) {
                return false;
            }
        }
        return true;
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
            return "wK";
        return "bK";
    }
}


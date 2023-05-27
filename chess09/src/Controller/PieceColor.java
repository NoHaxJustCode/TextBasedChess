package Controller;

/**
 * @authors Avinash Paluri and Vishal Patel
 *
 * Enum class that represents the two possible chess piece colors
 */

public enum PieceColor {

    /**
     * WHITE, BLACK 
     * 
     * represent the colors of the players
     */
    WHITE,BLACK;
    /**
	 * Gets the opposite color of the current player
	 * @return Returns the opposite color of the object
	 */
    public PieceColor opposite() {
        if (this == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }

    /**
     * String representation of the player colors.
     */
    public String toString() {
        if(this == BLACK)
            return "Black";
        return "White";
    }
}

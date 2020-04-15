package ldansorean.s4connect3;

public class Board {

    private Player board[][];
    private int remainingMoves;

    public Board() {
        this.board = new Player[3][3];
        this.remainingMoves = 9;
    }

    /**
     * Store the player move.
     * @param row The row index of the position marked by player. Zero based.
     * @param column The column index of the position marked by player. Zero based.
     * @param player The player that made the move.
     */
    public void markMove(int row, int column, Player player) {
        board[row][column] = player;
        remainingMoves--;
    }

    /**
     * @return the player that has won or null if there is none.
     */
    public Player getWinner() {
        for (int i = 0; i <= 2; i++) {
            //check line
            if (equals(board[i][0], board[i][1], board[i][2])) {
                return board[i][0];
            }
            //check column
            if (equals(board[0][i], board[1][i], board[2][i])) {
                return board[0][i];
            }
        }

        //check main diagonal
        if (equals(board[0][0], board[1][1], board[2][2])) {
            return board[1][1];
        }

        //check secondary diagonal
        if (equals(board[0][2], board[1][1], board[2][0])) {
            return board[1][1];
        }

        //if we got here there is no winner yet
        return null;
    }

    private boolean equals(Player p1, Player p2, Player p3) {
        return (p1 != null) && (p1.equals(p2)) && (p1.equals(p3));
    }

    /**
     * Returns true if the are available moves or false if the board is full.
     * @return true if there are more moves to be done or false otherwise.
     */
    public boolean areMovesAvailable() {
        return remainingMoves > 0;
    }

    /**
     * Checks if the position given by the row and column has not already been marked by a player.
     * @param row The row index of the position to check. Zero based.
     * @param column The column index of the position to check. Zero based.
     * @return true if the position is available or false if it's already been marked by one of the players.
     */
    public boolean isMoveValid(int row, int column) {
        return board[row][column] == null;
    }

}

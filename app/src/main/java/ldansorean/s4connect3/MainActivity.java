package ldansorean.s4connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int TOAST_DURATION = Toast.LENGTH_SHORT;
    private static final int ROTATION = 360 * 3;
    private static final int ANIMATION_DURATION = 500;

    private Player player1, player2, activePlayer;
    private Board board;
    private boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup game
        this.player1 = new Player(1, "player 1");
        this.player2 = new Player(2, "player 2");
        this.activePlayer = player1;
        this.board = new Board();

        findViewById(R.id.restartButton).setVisibility(View.INVISIBLE);
    }

    public void cellClicked(View view) {
        //get row and column clicked
        TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
        int column = params.column;
        TableLayout tableLayout = findViewById(R.id.table);
        int row = tableLayout.indexOfChild((TableRow) view.getParent());
        Log.i("click", String.format("row=%d, column = %d", row, column));

        markMove(row, column, (ImageView) view);
    }

    private void markMove(int row, int column, ImageView imageView) {
        if (gameEnded) {
            Toast.makeText(getApplicationContext(), "Game finished.", TOAST_DURATION).show();
        } else if (!board.isMoveValid(row, column)) {
            Toast.makeText(getApplicationContext(), "That position is already marked.", TOAST_DURATION).show();
        } else {
            board.markMove(row, column, activePlayer);
            displayPlayerToken(imageView);
            changeActivePlayer();

            //check for winner
            Player winner = board.getWinner();
            if (winner != null) {
                markEndGame();
                Toast.makeText(getApplicationContext(), winner.getName() + " has won!", TOAST_DURATION).show();
            } else if (!board.areMovesAvailable()) {
                markEndGame();
                Toast.makeText(getApplicationContext(), "It's a draw!", TOAST_DURATION).show();
            }
        }
    }

    private void markEndGame() {
        gameEnded = true;
        findViewById(R.id.restartButton).setVisibility(View.VISIBLE);
    }

    private void displayPlayerToken(ImageView cellImage) {
        //image setup
        cellImage.setImageResource(getPlayerTokenImage());
        cellImage.setScaleX(0);
        cellImage.setScaleY(0);

        //animate
        cellImage.animate().scaleX(1).scaleY(1).rotationBy(ROTATION).setDuration(ANIMATION_DURATION);
    }

    /**
     * @return The id of the resource to be used as player token.
     */
    private int getPlayerTokenImage() {
        return activePlayer.equals(player1) ? R.drawable.red : R.drawable.yellow;
    }

    private void changeActivePlayer() {
        activePlayer = activePlayer.equals(player1) ? player2 : player1;
    }

    public void restartGame(View view) {
        board.reset();
        gameEnded = false;
        findViewById(R.id.restartButton).setVisibility(View.INVISIBLE);

        //hide all images
        TableLayout tableLayout = findViewById(R.id.table);
        for (int rowIndex = 0; rowIndex < tableLayout.getChildCount(); rowIndex++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);
            for (int imageIndex = 0; imageIndex < tableRow.getChildCount(); imageIndex++) {
                ((ImageView) tableRow.getChildAt(imageIndex)).setImageResource(0);
            }
        }
    }

}

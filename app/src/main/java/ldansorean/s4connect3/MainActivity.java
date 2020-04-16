package ldansorean.s4connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
        this.player1 = new Player(1, "Red");
        this.player2 = new Player(2, "Yellow");
        this.activePlayer = player1;
        this.board = new Board();

        setEndOfGameLayoutVisibility(View.INVISIBLE);
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
        if (!board.isMoveValid(row, column) && !gameEnded) {
            Toast.makeText(getApplicationContext(), "That position is already marked.", TOAST_DURATION).show();
        } else if (!gameEnded){
            board.markMove(row, column, activePlayer);
            displayPlayerToken(imageView);
            changeActivePlayer();

            //check for winner
            Player winner = board.getWinner();
            if (winner != null) {
                endOfGame(winner.getName() + " has won!");
            } else if (!board.areMovesAvailable()) {
                endOfGame("It's a draw!");
            }
        }
    }

    private void endOfGame(String resultMessage) {
        gameEnded = true;

        setEndOfGameLayoutVisibility(View.VISIBLE);
        TextView endOfGameMessage = findViewById(R.id.endOfGameMessage);
        endOfGameMessage.setText(resultMessage);
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

        //hide all images
        TableLayout tableLayout = findViewById(R.id.table);
        for (int rowIndex = 0; rowIndex < tableLayout.getChildCount(); rowIndex++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);
            for (int imageIndex = 0; imageIndex < tableRow.getChildCount(); imageIndex++) {
                ((ImageView) tableRow.getChildAt(imageIndex)).setImageResource(0);
            }
        }

        setEndOfGameLayoutVisibility(View.INVISIBLE);
    }

    private void setEndOfGameLayoutVisibility(int visibility) {
        LinearLayout endOfGameLayout = findViewById(R.id.endOfGameLayout);
        endOfGameLayout.setVisibility(visibility);
    }

}

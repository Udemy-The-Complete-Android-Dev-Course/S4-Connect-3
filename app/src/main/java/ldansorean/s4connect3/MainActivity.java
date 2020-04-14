package ldansorean.s4connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int ROTATION = 360 * 3;
    private static int ANIMATION_DURATION = 500;

    private int activePlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cellClicked(View view) {
        displayPlayerToken((ImageView) view);
        changeActivePlayer();
    }

    private void displayPlayerToken(ImageView cellImage) {
        //image setup
        cellImage.setImageResource(getPlayerTokenImage());
        cellImage.setScaleX(0);
        cellImage.setScaleY(0);

        //animate
        cellImage.animate().scaleX(1).scaleY(1).rotationBy(ROTATION).setDuration(ANIMATION_DURATION);
    }

    private int getPlayerTokenImage() {
        return activePlayer == 0 ? R.drawable.red : R.drawable.yellow;
    }

    private void changeActivePlayer() {
        activePlayer = 1 - activePlayer;
    }
}

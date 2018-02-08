package com.example.gebruiker.timtactoe;

import android.service.quicksettings.Tile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GamePlay gamePlay;
    GridLayout gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gl = findViewById(R.id.tileLayout);

        // Check if we're restoring something
        if(savedInstanceState != null) {
            gamePlay = (GamePlay) savedInstanceState.getSerializable("GamePlayInstance");
        }
        else {
            gamePlay = new GamePlay();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reset_menu, menu);
        return true;
    }

    /* Passes coordinates of button in gridview to GamePlay class. */
    public void tileClicked(View view) {

        TileType tileType;

        // Check which tile was clicked, and update our behind the scenes array accordingly
        switch(view.getId()){
            case R.id.button0:
                tileType = gamePlay.validateTile(0, 0);
                break;
            case R.id.button1:
                tileType = gamePlay.validateTile(0, 1);
                break;
            case R.id.button2:
                tileType = gamePlay.validateTile(0, 2);
                break;
            case R.id.button3:
                tileType = gamePlay.validateTile(1, 0);
                break;
            case R.id.button4:
                tileType = gamePlay.validateTile(1, 1);
                break;
            case R.id.button5:
                tileType = gamePlay.validateTile(1, 2);
                break;
            case R.id.button6:
                tileType = gamePlay.validateTile(2, 0);
                break;
            case R.id.button7:
                tileType = gamePlay.validateTile(2, 1);
                break;
            case R.id.button8:
                tileType = gamePlay.validateTile(2, 2);
                break;
            default:
                tileType = TileType.INVALID;
        }

        // Update the UI
        updateTile(view, tileType);

        // Check if someone won or if it's a draw
        GameState gameState = gamePlay.checkForWinner();
        if(gameState == GameState.PLAYER_ONE || gameState == GameState.PLAYER_TWO) {
            makeToast(gameState.toString() + " is victorious!");
            toggleClicks();
        }
        else if(gameState == GameState.DRAW) {
            makeToast("Nobody wins! It's a draw.");
            toggleClicks();
        }
        else {
            Log.d("MainActivity", "no winner (yet)");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("GamePlayInstance", gamePlay);
    }

    public void toggleClicks(){
        // In an ideal world, this works, but alas
        gl.setClickable(!gl.isClickable());
    }

    public void restoreUI(){
        // something to restore the state of buttons?
    }

    /* Updates the "tile" to show the correct sign. */
    public void updateTile(View view, TileType tileType){

        Button b = (Button) view;

        if (tileType == TileType.CIRCLE) {
            b.setText("O");
        }
        else if (tileType == TileType.CROSS) {
            b.setText("X");
        }
        else {
            makeToast("That's an illegal move.");
        }
    }

    public void makeToast(String toastText) {
        Toast toast = Toast.makeText(getApplicationContext(), toastText,Toast.LENGTH_SHORT);
        toast.show();
    }

    /* Calls reset from game class and remakes Activity after. */
    public void resetGame(MenuItem item) {
        gamePlay.resetGame();
        recreate();
    }
}
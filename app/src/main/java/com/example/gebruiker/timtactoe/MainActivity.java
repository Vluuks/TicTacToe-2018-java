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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GamePlay gamePlay;
    GridLayout gl;
    Button[] buttonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gl = findViewById(R.id.tileLayout);

        // Initialize array to store buttons, for state res purposes
        buttonArray = new Button[]{
                findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8)
        };

        // Check if we're restoring something
        if(savedInstanceState != null) {
            gamePlay = (GamePlay) savedInstanceState.getSerializable("GamePlayInstance");
            restoreUI();
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

    public void restoreUI() {
        Log.d("reset UI called", "nice");

        // something to restore the state of buttons?
        for (Button button : buttonArray) {
            button.setText("nice test");
            Log.d("in loop", "yo");

            // This is so hideous i might cry
            switch(button.getId()){
                case R.id.button0:
                    updateTile(button, gamePlay.getTileContent(0, 0));
                    break;
                case R.id.button1:
                    updateTile(button, gamePlay.getTileContent(0, 1));
                    break;
                case R.id.button2:
                    updateTile(button, gamePlay.getTileContent(0, 2));
                    break;
                case R.id.button3:
                    updateTile(button, gamePlay.getTileContent(1, 0));
                    break;
                case R.id.button4:
                    updateTile(button, gamePlay.getTileContent(1, 1));
                    break;
                case R.id.button5:
                    updateTile(button, gamePlay.getTileContent(1, 2));
                    break;
                case R.id.button6:
                    updateTile(button, gamePlay.getTileContent(2, 0));
                    break;
                case R.id.button7:
                    updateTile(button, gamePlay.getTileContent(2, 1));
                    break;
                case R.id.button8:
                    updateTile(button, gamePlay.getTileContent(2, 2));
                    break;
            }
        }
    }

    /* Updates the "tile" to show the correct sign. */
    public void updateTile(View view, TileType tileType){

        Button b = (Button) view;

        switch(tileType){
            case CIRCLE:
                b.setText("O");
                break;
            case CROSS:
                b.setText("X");
                break;
            case BLANK:
                b.setText("");
                break;
            case INVALID:
                makeToast("Illegal move");
                break;
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
package com.example.gebruiker.timtactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GamePlay gamePlay;
    GridLayout tileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tileLayout = findViewById(R.id.tileLayout);

        gamePlay = new GamePlay(getApplicationContext(), tileLayout);

    }

    public void tileClicked(View view) {

        String id = Integer.toString(view.getId());
        Log.d("MainActivity", id);

        // Check which tile was clicked, and update our behind the scenes array accordingly
        switch(view.getId()){

            case R.id.button0:
                gamePlay.validateTile(0, 0, view);
                break;
            case R.id.button1:
                gamePlay.validateTile(0, 1, view);
                break;
            case R.id.button2:
                gamePlay.validateTile(0, 2, view);
                break;
            case R.id.button3:
                gamePlay.validateTile(1, 0, view);
                break;
            case R.id.button4:
                gamePlay.validateTile(1, 1, view);
                break;
            case R.id.button5:
                gamePlay.validateTile(1, 2, view);
                break;
            case R.id.button6:
                gamePlay.validateTile(2, 0, view);
                break;
            case R.id.button7:
                gamePlay.validateTile(2, 1, view);
                break;
            case R.id.button8:
                gamePlay.validateTile(2, 2, view);
                break;
        }

        // Check if someone won, if draw, reset
        // This is so clunky
        int winner = gamePlay.checkForWinner();
        if(winner == 1 || winner == 2) {
            Toast toast = Toast.makeText(getApplicationContext(),"Player " + String.valueOf(winner)+ " wins!",Toast.LENGTH_LONG);
            toast.show();

        }
        else if(winner == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),"Nobody wins!! You are all losers.",Toast.LENGTH_LONG);
            toast.show();

        }
        else {
            Log.d("MainActivity", "no winner (yet)");
        }
    }

    public void resetGame(View view) {
        gamePlay.resetGame();
        recreate();
    }
}

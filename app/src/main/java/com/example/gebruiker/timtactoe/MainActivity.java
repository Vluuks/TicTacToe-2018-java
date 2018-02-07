package com.example.gebruiker.timtactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Instantiate two-dimensional array to store the tiles
    // This will initialize to 0 by default
    int[][] tiles = new int[3][3];
    Boolean playerOneTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set player one's turn to true
        playerOneTurn = true;
    }

    public void validateTile(int row, int column, View view) {

        int tile = tiles[row][column];

        // If the tile is empty
        if(tile == 0) {

            // Grab the button
            Button b = (Button) view;

            if(playerOneTurn) {
                // make X
                tiles[row][column] = 1;
                b.setText("X");
            }
            else {
                tiles[row][column] = 2;
                b.setText("O");
            }

            // Change turns
            playerOneTurn = !playerOneTurn;
        }
        else {
            Toast toast=Toast.makeText(getApplicationContext(),"You can't do that",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }

    }

    public void tileClicked(View view) {

        String id = Integer.toString(view.getId());
        Log.d("MainActivity", id);

        // Check which tile was clicked, and update our behind the scenes array accordingly
        switch(view.getId()){

            case R.id.button0:
                validateTile(0, 0, view);
                break;
            case R.id.button1:
                validateTile(0, 1, view);
                break;
            case R.id.button2:
                validateTile(0, 2, view);
                break;
            case R.id.button3:
                validateTile(1, 0, view);
                break;
            case R.id.button4:
                validateTile(1, 1, view);
                break;
            case R.id.button5:
                validateTile(1, 2, view);
                break;
            case R.id.button6:
                validateTile(2, 0, view);
                break;
            case R.id.button7:
                validateTile(2, 1, view);
                break;
            case R.id.button8:
                validateTile(2, 2, view);
                break;
        }
    }
}

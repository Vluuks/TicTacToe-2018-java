package com.example.gebruiker.timtactoe;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 * Created by Gebruiker on 7-2-2018.
 */

public class GamePlay {

    Context context;
    GridLayout tileLayout;
    int[][] tiles;
    Boolean playerOneTurn;
    int BOARD_SIZE = 3;
    int movesPlayed;
    Boolean gameOver;

    // Global so win function can access them
    int tileRow, tileColumn;

    public GamePlay(Context context, GridLayout tileLayout){

        // Initialize array and set player one's turn to true
        this.context = context;
        this.tileLayout = tileLayout;

        tiles = new int[BOARD_SIZE][BOARD_SIZE];

        playerOneTurn = true;
        gameOver = false;
        movesPlayed = 0;
    }

    public void validateTile(int clickedTileRow, int clickedTileColumn, View view) {

        tileRow = clickedTileRow;
        tileColumn = clickedTileColumn;

        int tile = tiles[tileRow][tileColumn];

        // If the tile is empty
        if(tile == 0) {

            // Grab the button
            Button b = (Button) view;

            // Mark tiles in array with player number
            if(playerOneTurn) {
                // X
                tiles[tileRow][tileColumn] = 1;
                b.setText("X");
            }
            else {
                // O
                tiles[tileRow][tileColumn] = 2;
                b.setText("O");
            }

            // Add move and change turns
            movesPlayed++;
            playerOneTurn = !playerOneTurn;

        }
        else {
            Toast toast=Toast.makeText(context,"You can't do that",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }

    }

    public void resetGame(){

        tiles = new int[BOARD_SIZE][BOARD_SIZE];
        playerOneTurn = true;
        tileLayout.invalidate();

    }

    public int checkForWinner() {

        int currentPlayerTile;

        // Check who was the last one to mark a tile
        if(!playerOneTurn) {
            // X
            // Then player one was the last one
            currentPlayerTile = 1;
        }
        else {
            // O
            // Player two was the last one
            currentPlayerTile = 2;
        }

        // Check coordinates in row of last played tile
        for(int i = 0; i < BOARD_SIZE; i++) {
            if(tiles[tileRow][i] != currentPlayerTile) {
                break;
            }
            if(i == BOARD_SIZE - 1) {
                return currentPlayerTile;
            }
        }

        // Check the coordinates in the column of last played tile
        for(int i = 0; i < BOARD_SIZE; i++) {
            if(tiles[i][tileColumn] != currentPlayerTile) {
                break;
            }
            if(i == BOARD_SIZE - 1) {
                return currentPlayerTile;
            }
        }

        // Check diagonal left to right
        if(tileRow == tileColumn){
            for(int i = 0; i < BOARD_SIZE; i++) {
                if(tiles[i][i] != currentPlayerTile){
                    break;
                }
                if(i == BOARD_SIZE - 1) {
                    return currentPlayerTile;
                }
            }

        }

        // Check diagonal right to left
        if (tileRow + tileColumn == BOARD_SIZE - 1) {
            for(int i = 0; i < BOARD_SIZE; i++) {

                if(tiles[i][BOARD_SIZE - (1 + i)] != currentPlayerTile){
                    break;
                }
                if(i == BOARD_SIZE - 1) {
                    return currentPlayerTile;
                }
            }
        }

        if(movesPlayed == 9){
            return 0;
        }
        return -1;
    }
}

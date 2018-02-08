package com.example.gebruiker.timtactoe;

import android.content.Context;
import android.widget.GridLayout;

import java.io.Serializable;

/**
 * Created by Gebruiker on 7-2-2018.
 */

public class GamePlay implements Serializable {

    Context context;
    GridLayout tileLayout;
    int[][] tiles;
    Boolean playerOneTurn;
    int BOARD_SIZE = 3;
    int movesPlayed;
    Boolean gameOver;

    // Global so win function can access them
    int tileRow, tileColumn;

    public GamePlay(){

        tiles = new int[BOARD_SIZE][BOARD_SIZE];

        playerOneTurn = true;
        gameOver = false;
        movesPlayed = 0;
    }

    /* Checks whether to mark a X or O or if the move is illegal. Returns the
     * appropriate tile type. */
    public TileType validateTile(int clickedTileRow, int clickedTileColumn) {

        tileRow = clickedTileRow;
        tileColumn = clickedTileColumn;

        int tile = tiles[tileRow][tileColumn];
        TileType type;

        // If the tile is empty in our array
        if(tile == 0) {

            // Mark tiles in array with player number
            if(playerOneTurn) {
                tiles[tileRow][tileColumn] = 1;
                type = TileType.CROSS;
            }
            else {
                tiles[tileRow][tileColumn] = 2;
                type = TileType.CIRCLE;
            }

            // Add move and change turns
            movesPlayed++;
            playerOneTurn = !playerOneTurn;

        }
        // If it's already taken, the move is illegal
        else {
            type = TileType.INVALID;
        }

        return type;
    }

    /* Resets the board to empty again and makes it player one's turn. */
    public void resetGame(){

        tiles = new int[BOARD_SIZE][BOARD_SIZE];
        playerOneTurn = true;

    }

    /* Checks if win-condition is met based on the last valid tile that was touched,
    * checking its diagonal(s), row and column. */
    public GameState checkForWinner() {

        int currentPlayerTile;
        GameState winner;

        // Check who was the last one to mark a tile
        if(!playerOneTurn) {
            // X
            // Then player one was the last one
            currentPlayerTile = 1;
            winner = GameState.PLAYER_ONE;

        }
        else {
            // O
            // Player two was the last one
            currentPlayerTile = 2;
            winner = GameState.PLAYER_TWO;
        }

        // Check coordinates in row of last played tile
        for(int i = 0; i < BOARD_SIZE; i++) {
            if(tiles[tileRow][i] != currentPlayerTile) {
                break;
            }
            if(i == BOARD_SIZE - 1) {
                return winner;
            }
        }

        // Check the coordinates in the column of last played tile
        for(int i = 0; i < BOARD_SIZE; i++) {
            if(tiles[i][tileColumn] != currentPlayerTile) {
                break;
            }
            if(i == BOARD_SIZE - 1) {
                return winner;
            }
        }

        // Check diagonal left to right
        if(tileRow == tileColumn){
            for(int i = 0; i < BOARD_SIZE; i++) {
                if(tiles[i][i] != currentPlayerTile){
                    break;
                }
                if(i == BOARD_SIZE - 1) {
                    return winner;
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
                    return winner;
                }
            }
        }

        // If nobody has won yet at 9 moves, it's a draw
        if(movesPlayed == 9){
            return GameState.DRAW;
        }

        return GameState.IN_PROGRESS;
    }
}

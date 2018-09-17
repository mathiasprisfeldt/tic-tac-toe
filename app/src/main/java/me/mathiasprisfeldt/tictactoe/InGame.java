package me.mathiasprisfeldt.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePiece;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceAdapter;

public class InGame extends AppCompatActivity {

    private GamePiece[][] _gamePieces = new GamePiece[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        InitializeGamePieces();
        GamePieceAdapter gamePieceAdapter = new GamePieceAdapter(this, _gamePieces);

        GridView gw = findViewById(R.id.game_grid);
        gw.setAdapter(gamePieceAdapter);
    }

    private void InitializeGamePieces() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                _gamePieces[x][y] = new GamePiece();
            }
        }
    }

    private void ResetGamePieces() {
        for (GamePiece[] rowPieces : _gamePieces) {
            for (GamePiece piece : rowPieces) {
                piece.Reset();
            }
        }
    }
}

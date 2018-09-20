package me.mathiasprisfeldt.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePiece;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceAdapter;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceType;

public class InGame extends AppCompatActivity {

    private TextView _statusText;
    private GamePieceType _currentPlayer;
    private GamePiece[][] _gamePieces = new GamePiece[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        InitializeGamePieces();
        GamePieceAdapter gamePieceAdapter = new GamePieceAdapter(this, _gamePieces);

        GridView gw = findViewById(R.id.game_grid);
        gw.setAdapter(gamePieceAdapter);

        findViewById(R.id.game_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetGame();
            }
        });

        _statusText = findViewById(R.id.game_status);

        SetPlayer(GamePieceType.Ply1);
    }

    private void InitializeGamePieces() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                _gamePieces[x][y] = new GamePiece(this);
            }
        }
    }

    private void ResetGame() {
        for (GamePiece[] rowPieces : _gamePieces) {
            for (GamePiece piece : rowPieces) {
                piece.Reset();
            }
        }

        SetPlayer(GamePieceType.Ply1);
    }

    private void SetPlayer(GamePieceType ply) {
        _currentPlayer = ply;
        _statusText.setText(ply == GamePieceType.Ply1 ? "Player 1" : "Player 2");
    }

    public void EndTurn() {
        SetPlayer(_currentPlayer == GamePieceType.Ply1 ? GamePieceType.Ply2 : GamePieceType.Ply1);
    }

    public GamePieceType GetCurrentPlayer() {
        return _currentPlayer;
    }
}

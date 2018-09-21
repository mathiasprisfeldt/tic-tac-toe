package me.mathiasprisfeldt.tictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePiece;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceAdapter;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceType;

public class InGame extends AppCompatActivity {

    private GridView _grid;
    private TextView _statusText;

    private Player _ply1;
    private Player _ply2;
    private Player _currentPlayer;

    private GamePiece[][] _gamePieces = new GamePiece[3][3];

    public Player GetCurrentPlayer() {
        return _currentPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        InitializeGamePieces();
        GamePieceAdapter gamePieceAdapter = new GamePieceAdapter(this, _gamePieces);

        _grid = findViewById(R.id.game_grid);
        _grid.setAdapter(gamePieceAdapter);

        findViewById(R.id.game_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetGame();
            }
        });

        findViewById(R.id.game_goto_main_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InGame.this, MainMenu.class));
            }
        });

        _statusText = findViewById(R.id.game_status);

        if (_ply1 == null)
            _ply1 = new Player(getString(R.string.player_1), this, GamePieceType.Cross);

        if (_ply2 == null)
            _ply2 = new Player(getString(R.string.player_2), this, GamePieceType.Circle);

        ResetGame();
    }

    private void InitializeGamePieces() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                _gamePieces[y][x] = new GamePiece(this, x + (y * 3));
            }
        }
    }

    private void ResetGame() {
        for (GamePiece[] rowPieces : _gamePieces) {
            for (GamePiece piece : rowPieces) {
                piece.Reset();
            }
        }

        _ply1.Reset();
        _ply2.Reset();

        SetPlayer(_ply1);

        _grid.setBackgroundResource(R.drawable.ic_game_board);
    }

    private void SetPlayer(Player ply) {
        _currentPlayer = ply;


        String plyName = ply.toString();

        String action = ply.getPieceAmount() < 3 ?
                getString(R.string.game_status_action_place) :
                getString(R.string.game_status_action_remove);

        String piece = ply.getPieceType() == GamePieceType.Circle ?
                getString(R.string.game_piece_circle) :
                getString(R.string.game_piece_cross);

        _statusText.setText(String.format("%s - %s %s", plyName, action, piece));

    }

    public void EndTurn(boolean removedPiece) {
        if (removedPiece)
            SetPlayer(_currentPlayer);
        else {
            _grid.setBackgroundResource(_currentPlayer.getPieceType() == GamePieceType.Circle ?
                    R.drawable.ic_game_board_circle :
                    R.drawable.ic_game_board_cross);

            if (CheckWinCon(_currentPlayer))
                _statusText.setText(getString(R.string.game_status_winning, _currentPlayer.toString()));
            else
                SetPlayer(_currentPlayer == _ply1 ? _ply2 : _ply1);
        }
    }

    public boolean CheckWinCon(Player player) {
        GamePiece lastPiece = null;
        int lastDelta = -1;

        for (int y = 0; y < _gamePieces.length; y++) {
            for (int x = 0; x < 3; x++) {
                GamePiece piece = _gamePieces[x][y];

                if (piece.getOwner() == player) {
                    int delta = -1;

                    if (lastPiece != null)
                        delta = Math.abs(lastPiece.getIndex() - piece.getIndex());

                    if (lastDelta != -1 && lastDelta == delta)
                        return true;

                    lastPiece = piece;
                    lastDelta = delta;
                }
            }
        }

        return false;
    }
}

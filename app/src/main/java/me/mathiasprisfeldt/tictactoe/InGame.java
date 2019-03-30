package me.mathiasprisfeldt.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceAdapter;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceType;

public class InGame extends AppCompatActivity {

    private int _gamemode;
    private boolean _isAiGame;
    private boolean _isGameover;

    private GameBoard _board;
    private GridView _grid;
    private TextView _statusText;

    private Player _ply1;
    private Player _ply2;
    private Player _currentPlayer;

    public int GetGamemode() {
        return _gamemode;
    }

    public boolean GetIsGameover() {
        return _isGameover;
    }

    public boolean GetIsAiGame() {
        return _isAiGame;
    }

    public Player GetOtherPlayer(Player ply) {
        if (ply == _ply1)
            return _ply2;
        else
            return _ply1;
    }

    public Player GetCurrentPlayer() {
        return _currentPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        Intent intent = getIntent();

        _board = new GameBoard(this);

        GamePieceAdapter gamePieceAdapter = new GamePieceAdapter(this, _board.getPieces());

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

        _gamemode = intent.getIntExtra("gamemode", R.id.against_player);

        if (_ply1 == null) {
            _ply1 = new Player(false, getString(R.string.player_1), this, GamePieceType.Cross);
            _isAiGame = false;
        }

        if (_ply2 == null) {

            boolean isAi = _gamemode != R.id.against_player;
            _ply2 = new Player(isAi, getString(R.string.player_2), this, GamePieceType.Circle);
            _isAiGame = isAi;
        }

        ResetGame();
    }

    private void ResetGame() {
        _board.Reset();

        _ply1.Reset();
        _ply2.Reset();

        SetPlayer(_ply1);

        _grid.setBackgroundResource(R.drawable.ic_game_board);

        _isGameover = false;
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

        _currentPlayer.YourTurn(_board);
    }

    public void EndTurn(boolean removedPiece) {
        if (removedPiece)
            SetPlayer(_currentPlayer);
        else {
            _grid.setBackgroundResource(_currentPlayer.getPieceType() == GamePieceType.Circle ?
                    R.drawable.ic_game_board_circle :
                    R.drawable.ic_game_board_cross);

            if (_board.IsBoardFilled()) {
                _statusText.setText(R.string.game_draw_text);
                _grid.setBackgroundResource(R.drawable.ic_game_board);
                _isGameover = true;
            } else if (_board.CheckWinCon(_currentPlayer.getPieceType())) {
                _statusText.setText(getString(R.string.game_status_winning, _currentPlayer.toString()));
                _isGameover = true;
            } else
                SetPlayer(_currentPlayer == _ply1 ? _ply2 : _ply1);
        }
    }
}

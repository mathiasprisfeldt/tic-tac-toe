package me.mathiasprisfeldt.tictactoe.GamePiece;

import android.view.View;
import android.widget.ImageButton;

import me.mathiasprisfeldt.tictactoe.InGame;
import me.mathiasprisfeldt.tictactoe.Player;

public class GamePiece {

    private int _index;
    private InGame _context;
    private boolean _isInitialized;
    private Player _owner;
    private ImageButton _imageBtn;

    public int getIndex() {
        return _index;
    }

    public Player getOwner() {
        return _owner;
    }

    public GamePieceType getGamePieceType() {
        if (_owner != null)
            return _owner.getPieceType();

        return GamePieceType.None;
    }

    public GamePiece(InGame context, int index) {
        _context = context;
        _index = index;
    }

    public GamePiece(InGame context, int index, Player owner) {
        _context = context;
        _index = index;
        _owner = owner;
    }

    public void UpdateBtn(ImageButton imageBtn) {
        if (_isInitialized)
            return;

        _imageBtn = imageBtn;
        _imageBtn.setOnClickListener(OnClick);

        _isInitialized = true;
    }

    private final View.OnClickListener OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Player player = _context.GetCurrentPlayer();

            if (!player.getIsAi())
                player.SetPiece(GamePiece.this);
        }
    };

    public void SetOwner(Player owner) {
        _owner = owner;

        if (_imageBtn == null)
            return;

        if (_owner != null) {
            _imageBtn.setImageResource(_owner.getPieceImage());
        }
        else
            _imageBtn.setImageResource(android.R.color.transparent);
    }

    @Override
    public GamePiece clone() {
        return new GamePiece(_context, _index, _owner);
    }

    public void Reset() {
        SetOwner(null);
    }
}

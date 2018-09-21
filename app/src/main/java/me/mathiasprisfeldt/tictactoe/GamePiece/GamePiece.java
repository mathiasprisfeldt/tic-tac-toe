package me.mathiasprisfeldt.tictactoe.GamePiece;

import android.view.View;
import android.widget.ImageButton;

import me.mathiasprisfeldt.tictactoe.InGame;
import me.mathiasprisfeldt.tictactoe.Player;
import me.mathiasprisfeldt.tictactoe.R;

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

    public GamePiece(InGame context, int index) {
        _context = context;
        _index = index;
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
            _context.GetCurrentPlayer().SetPiece(GamePiece.this);
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

    public void Reset() {
        SetOwner(null);
    }
}

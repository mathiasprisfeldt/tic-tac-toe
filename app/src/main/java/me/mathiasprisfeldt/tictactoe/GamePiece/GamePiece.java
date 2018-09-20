package me.mathiasprisfeldt.tictactoe.GamePiece;

import android.view.View;
import android.widget.ImageButton;

import me.mathiasprisfeldt.tictactoe.InGame;
import me.mathiasprisfeldt.tictactoe.R;

public class GamePiece {

    private InGame _context;
    private boolean _isInitialized;
    private GamePieceType _type;
    private ImageButton _imageBtn;

    public GamePiece(InGame context) {
        _context = context;
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
            SetOwner(_context.GetCurrentPlayer());
            _context.EndTurn();
        }
    };

    private void SetOwner(GamePieceType owner) {
        _type = owner;

        switch (_type) {
            case None:
                _imageBtn.setImageResource(android.R.color.transparent);
                break;
            case Ply1:
                _imageBtn.setImageResource(R.drawable.ic_circle);
                break;
            case Ply2:
                _imageBtn.setImageResource(R.drawable.ic_cross);
                break;
        }
    }

    public void Reset() {
        SetOwner(GamePieceType.None);
    }
}

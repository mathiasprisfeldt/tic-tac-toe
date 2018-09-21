package me.mathiasprisfeldt.tictactoe;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePiece;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceType;

public class Player {
    private String _name;
    private InGame _context;
    private GamePieceType _pieceType;
    private int _pieceAmount;

    public int getPieceImage() {
        switch (_pieceType) {
            case Cross:
                return R.drawable.ic_cross;
            case Circle:
                return R.drawable.ic_circle;
        }

        return android.R.color.transparent;
    }

    public GamePieceType getPieceType() {
        return _pieceType;
    }

    public int getPieceAmount() {
        return _pieceAmount;
    }

    Player(String name, InGame context, GamePieceType pieceType) {
        _name = name;
        _context = context;
        _pieceType = pieceType;
    }

    public void SetPiece(GamePiece piece) {
        Player owner = piece.getOwner();

        if (_pieceAmount < 3 && owner == null) {
            piece.SetOwner(this);
            _pieceAmount++;
            _context.EndTurn(false);
        }
        else if (_pieceAmount >= 3 && owner == this) {
            piece.SetOwner(null);
            _pieceAmount--;
            _context.EndTurn(true);
        }
    }

    public void Reset() {
        _pieceAmount = 0;
    }

    @Override
    public String toString() {
        return _name;
    }
}

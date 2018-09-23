package me.mathiasprisfeldt.tictactoe.GamePiece;

public enum GamePieceType {
    None,
    Cross,
    Circle;

    public GamePieceType invert() {
        if (this == GamePieceType.None)
            return GamePieceType.None;

        return this == GamePieceType.Circle ? GamePieceType.Cross : GamePieceType.Circle;
    }
}

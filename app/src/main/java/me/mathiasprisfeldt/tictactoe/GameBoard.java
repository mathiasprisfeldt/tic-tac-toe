package me.mathiasprisfeldt.tictactoe;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePiece;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceType;

public class GameBoard {
    private GamePiece[] _pieces = new GamePiece[9];

    public GamePiece[] getPieces() {
        return _pieces;
    }

    GameBoard(InGame context) {
        for (int i = 0; i < _pieces.length; i++) {
            _pieces[i] = new GamePiece(context, i);
        }
    }

    private GameBoard() {

    }

    private boolean GetWinRow(int index, int index2, int index3, GamePieceType typeToCheck) {
        GamePieceType indexType = _pieces[index].getGamePieceType();
        GamePieceType indexType2 = _pieces[index2].getGamePieceType();
        GamePieceType indexType3 = _pieces[index3].getGamePieceType();

        return indexType == typeToCheck &&
                indexType2 == typeToCheck &&
                indexType3 == typeToCheck;
    }

    public boolean CheckWinCon(GamePieceType pieceType) {

        boolean weWon = false;
        weWon |= GetWinRow(0, 1, 2, pieceType);
        weWon |= GetWinRow(3, 4, 5, pieceType);
        weWon |= GetWinRow(6, 7, 8, pieceType);

        weWon |= GetWinRow(0, 3, 6, pieceType);
        weWon |= GetWinRow(1, 4, 7, pieceType);
        weWon |= GetWinRow(2, 5, 8, pieceType);

        weWon |= GetWinRow(0, 4, 8, pieceType);
        weWon |= GetWinRow(2, 4, 6, pieceType);

        return weWon;

        /*GamePiece lastPiece = null;
        int lastDelta = -1;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int index = y + (x * 3);

                GamePiece piece = _pieces[index];

                Player owner = piece.getOwner();

                if (owner == null)
                    continue;

                if (owner.getPieceType() == pieceType) {
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

        return false;*/
    }

    public boolean IsBoardFilled() {
        boolean boardFilled = true;

        for (GamePiece piece : _pieces) {
            if (piece.getOwner() == null)
                boardFilled = false;
        }

        return boardFilled;
    }

    public int checkScore(GamePieceType pieceType, int depth) {
        if (CheckWinCon(pieceType))
            return 10 - depth;
        else if (CheckWinCon(pieceType.invert()))
            return depth - 10;

        return 0;
    }

    public int checkScore(Player ply, int depth) {
        return checkScore(ply.getPieceType(), depth);
    }

    public int pieceAmount(GamePieceType pieceType) {
        int pieceCounter = 0;

        for (GamePiece piece : _pieces) {
            Player owner = piece.getOwner();

            if (owner != null && owner.getPieceType() == pieceType)
                pieceCounter++;
        }

        return pieceCounter;
    }

    public int pieceAmount(Player ply) {
        return pieceAmount(ply.getPieceType());
    }

    @Override
    public GameBoard clone()  {
        GameBoard boardClone = new GameBoard();

        GamePiece[] pieces = boardClone.getPieces();

        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = _pieces[i].clone();
        }

        return boardClone;
    }

    public void Reset() {
        for (GamePiece piece : _pieces) {
            piece.Reset();
        }
    }
}

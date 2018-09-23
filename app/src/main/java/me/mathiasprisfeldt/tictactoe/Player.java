package me.mathiasprisfeldt.tictactoe;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.mathiasprisfeldt.tictactoe.GamePiece.GamePiece;
import me.mathiasprisfeldt.tictactoe.GamePiece.GamePieceType;

public class Player {
    private boolean _isAi;
    private String _name;
    private InGame _context;
    private GamePieceType _pieceType;
    private int _pieceAmount;
    private int _move;

    public boolean getIsAi() {
        return _isAi;
    }

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

    Player(boolean isAi, String name, InGame context, GamePieceType pieceType) {
        _isAi = isAi;
        _name = isAi ? context.getString(R.string.player_ai) : name;
        _context = context;
        _pieceType = pieceType;
    }

    public void SetPiece(GamePiece piece) {
        if (_context.GetIsGameover())
            return;

        Player owner = piece.getOwner();

        boolean canAdd = _pieceAmount < 3;

        if (_context.GetIsAiGame())
            canAdd = true;

        if (canAdd && owner == null) {
            piece.SetOwner(this);
            _pieceAmount++;
            _context.EndTurn(false);
        }
        else if (!canAdd && owner == this) {
            piece.SetOwner(null);
            _pieceAmount--;
            _context.EndTurn(true);
        }
    }

    public void SetPiece(GamePiece piece, GamePiece toReplace) {
        toReplace.SetOwner(null);
        SetPiece(piece);
    }

    public void YourTurn(GameBoard gameBoard) {
        if (!_isAi)
            return;

        miniMax(gameBoard, this, 0);

        SetPiece(gameBoard.getPieces()[_move]);
    }

    public int miniMax(GameBoard board,  Player currPly, int depth) {

        GameBoard clone = board.clone();

        int ourScore = clone.checkScore(this, depth);
        if (ourScore != 0)
            return ourScore;

        boolean allFilled = true;
        for (GamePiece gamePiece : clone.getPieces()) {
            if (gamePiece.getOwner() == null)
                allFilled = false;
        }
        if (allFilled)
            return 0;

        GamePiece[] pieces = clone.getPieces();

        ArrayList<Integer> moves = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        Player otherPlayer = _context.GetOtherPlayer(currPly);

        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getOwner() == null) {
                scores.add(miniMax(makeMove(clone, i, currPly), otherPlayer, depth + 1));
                moves.add(i);
            }
        }

        int bestScore = currPly.getIsAi() ? -10000 : 10000;

        for (int i = 0; i < scores.size(); i++) {
            Integer score = scores.get(i);

            if (currPly.getIsAi()) {
                if (score > bestScore) {
                    bestScore = score;
                    _move = moves.get(i);
                }
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    _move = moves.get(i);

                }
            }

        }

        return bestScore;
    }

    public GameBoard makeMove(GameBoard board, int move, Player ply) {
        GameBoard newBoard = board.clone();
        newBoard.getPieces()[move].SetOwner(ply);
        return newBoard;
    }

    public void Reset() {
        _pieceAmount = 0;
    }

    @Override
    public String toString() {
        return _name;
    }
}

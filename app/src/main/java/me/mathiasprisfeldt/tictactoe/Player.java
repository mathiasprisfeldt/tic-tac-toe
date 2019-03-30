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

    public void SetPiece(GamePiece piece, boolean endTurn) {
        if (_context.GetIsGameover())
            return;

        Player owner = piece.getOwner();

        boolean canAdd = _pieceAmount < 3;

        if (_context.GetGamemode() == R.id.against_ai)
            canAdd = true;

        if (canAdd && owner == null) {
            piece.SetOwner(this);
            _pieceAmount++;

            if (endTurn)
                _context.EndTurn(false);
        }
        else if (!canAdd && owner == this) {
            piece.SetOwner(null);
            _pieceAmount--;

            if (endTurn)
                _context.EndTurn(true);
        }
    }

    public void SetPiece(GamePiece piece) {
        SetPiece(piece, true);
    }

    public void SetPiece(GamePiece piece, GamePiece toReplace) {
        toReplace.SetOwner(null);
        SetPiece(piece);
    }

    public void YourTurn(GameBoard gameBoard) {
        if (!_isAi)
            return;

        PlayerMove move = miniMax(gameBoard, this, 0, Integer.MAX_VALUE);

        if (move.toRemove != -1)
            SetPiece(gameBoard.getPieces()[move.toRemove], false);

        SetPiece(gameBoard.getPieces()[move.move]);
    }

    public PlayerMove miniMax(GameBoard board,  Player currPly, int depth, int maxDepth) {
        GameBoard clone = board.clone();

        int ourScore = clone.checkScore(this, depth);
        if (ourScore != 0)
            return new PlayerMove(ourScore);

        if (clone.IsBoardFilled())
            return new PlayerMove(0);

        GamePiece[] pieces = clone.getPieces();
        ArrayList<PlayerMove> moves = new ArrayList<>();

        Player otherPlayer = _context.GetOtherPlayer(currPly);

        int pieceAmount = currPly.getPieceAmount();

        if (_context.GetGamemode() == R.id.against_ai)
            pieceAmount = 0;

        if (pieceAmount < 3) {
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i].getOwner() == null) {
                    PlayerMove newMove = miniMax(makeMove(clone, i, currPly), otherPlayer, depth + 1, Integer.MAX_VALUE);
                    newMove.move = i;
                    moves.add(newMove);
                }
            }
        } else if (depth < maxDepth) {
            for (int ourPiece = 0; ourPiece < pieces.length; ourPiece++) {

                if (pieces[ourPiece].getOwner() != currPly)
                    continue;

                for (int newPiece = 0; newPiece < pieces.length; newPiece++) {
                    if (pieces[newPiece].getOwner() == null) {
                        GameBoard makeMove = makeMove(clone, newPiece, currPly);
                        makeMove.getPieces()[ourPiece].SetOwner(null);
                        PlayerMove newMove = miniMax(makeMove, otherPlayer, depth + 1, maxDepth == Integer.MAX_VALUE ? depth + 2 : depth + 1);

                        newMove.move = newPiece;
                        newMove.toRemove = ourPiece;
                        moves.add(newMove);
                    }
                }
            }
        }

        PlayerMove move = new PlayerMove(0);

        if (moves.size() > 0) {
            if (currPly.getIsAi()) {
                move = Collections.max(moves);
            } else {
                move = Collections.min(moves);
            }
        }

        if (pieceAmount < 3)
            move.toRemove = -1;

        return move;
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

    private class PlayerMove implements Comparable<PlayerMove> {
        public int score;
        public int move = -1;
        public int toRemove = -1;

        public PlayerMove(int score, int move, int toRemove) {
            this.score = score;
            this.move = move;
            this.toRemove = toRemove;
        }

        public PlayerMove(int score, int move) {
            this.score = score;
            this.move = move;
        }

        public PlayerMove(int score) {
            this.score = score;
        }

        @Override
        public int compareTo(@NonNull PlayerMove o) {
            return Integer.compare(score, o.score);
        }
    }
}

package me.mathiasprisfeldt.tictactoe.GamePiece;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import me.mathiasprisfeldt.tictactoe.Extensions.ArrayExtensions;
import me.mathiasprisfeldt.tictactoe.R;
public class GamePieceAdapter extends ArrayAdapter<GamePiece> {

    private final Context _context;

    public GamePieceAdapter(@NonNull Context context, @NonNull GamePiece[] gamePieces) {
        super(context, 0, gamePieces);
        _context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GamePiece gamePiece = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(
                    R.layout.adapter_game_piece,
                    parent,
                    false
            );

        }

        if (gamePiece == null) {
            Log.w("GamePieceAdapter", "Game Piece was NULL, it shouldn't be.");
            return convertView;
        }

        gamePiece.UpdateBtn((ImageButton) convertView.findViewById(R.id.game_piece_btn));

        return convertView;
    }
}

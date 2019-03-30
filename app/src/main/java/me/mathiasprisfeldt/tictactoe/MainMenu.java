package me.mathiasprisfeldt.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        findViewById(R.id.against_player).setOnClickListener(this);
        findViewById(R.id.against_ai).setOnClickListener(this);
        findViewById(R.id.against_ai_real).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent newIntent = new Intent(this, InGame.class);
        newIntent.putExtra("gamemode", v.getId());
        startActivity(newIntent);
    }
}

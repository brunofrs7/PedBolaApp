package pt.brunofrsantos.pedbolaapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pt.brunofrsantos.pedbolaapp.R;

public class MenuActivity extends AppCompatActivity {
    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.mViewHolder.bt_registarJogo = findViewById(R.id.bt_menu_registarJogo);
        this.mViewHolder.bt_verJogador = findViewById(R.id.bt_menu_verJogador);
        this.mViewHolder.bt_novoJogador = findViewById(R.id.bt_menu_novoJogador);

        mViewHolder.bt_verJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, VerJogadorActivity.class));
            }
        });
        mViewHolder.bt_registarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, NovoJogoActivity.class));
            }
        });
        mViewHolder.bt_novoJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, NovoJogadorActivity.class));
            }
        });
    }

    private static class ViewHolder {
        Button bt_verJogador;
        Button bt_registarJogo;
        Button bt_novoJogador;
    }
}

package pt.brunofrsantos.pedbolaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NovoJogadorActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogador);

        viewHolder.bt_novoJogador_registar = findViewById(R.id.bt_novoJogador_registar);
        viewHolder.et_novoJogador_telefone = findViewById(R.id.et_novoJogador_telefone);
        viewHolder.et_novoJogador_nome = findViewById(R.id.et_novoJogador_nome);

        viewHolder.bt_novoJogador_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = viewHolder.et_novoJogador_nome.getText().toString();
                String telefone = viewHolder.et_novoJogador_telefone.getText().toString();

                if (nome.trim().isEmpty()) {
                    Toast.makeText(NovoJogadorActivity.this,
                            "Preencha nome do jogador", Toast.LENGTH_SHORT).show();
                    return;
                }

                CollectionReference jogadorRef = FirebaseFirestore.getInstance()
                        .collection("Jogador");
                jogadorRef.add(new Jogador(nome, telefone, 0));
                Toast.makeText(NovoJogadorActivity.this, "Jogador adicionado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private class ViewHolder {
        EditText et_novoJogador_nome;
        EditText et_novoJogador_telefone;
        Button bt_novoJogador_registar;
    }
}

package pt.brunofrsantos.pedbolaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DadosJogadorActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_jogador);
        viewHolder.bt_dadosJogador_cancelar = findViewById(R.id.bt_dadosJogador_cancelar);
        viewHolder.bt_dadosJogador_eliminar = findViewById(R.id.bt_dadosJogador_eliminar);
        viewHolder.bt_dadosJogador_editar = findViewById(R.id.bt_dadosJogador_editar);
        viewHolder.et_dadosJogador_njogos = findViewById(R.id.et_dadosJogador_njogos);
        viewHolder.et_dadosJogador_telefone = findViewById(R.id.et_dadosJogador_telefone);
        viewHolder.et_dadosJogador_nome = findViewById(R.id.et_dadosJogador_nome);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        String nome = i.getStringExtra("nome");
        String telefone = i.getStringExtra("telefone");
        int njogos = i.getIntExtra("njogos", 0);

        viewHolder.et_dadosJogador_nome.setText(nome);
        viewHolder.et_dadosJogador_telefone.setText(telefone);
        viewHolder.et_dadosJogador_njogos.setText(String.valueOf(njogos));

        viewHolder.bt_dadosJogador_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewHolder.bt_dadosJogador_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Jogador").document(id).update(
                        "nome", viewHolder.et_dadosJogador_nome.getText().toString(),
                        "telefone", viewHolder.et_dadosJogador_telefone.getText().toString(),
                        "njogos", Integer.parseInt(viewHolder.et_dadosJogador_njogos.getText().toString())
                );
                Toast.makeText(DadosJogadorActivity.this, "Jogador editado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        viewHolder.bt_dadosJogador_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Jogador").document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DadosJogadorActivity.this, "Jogador eliminado com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DadosJogadorActivity.this, "Erro ao eliminar jogador", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private class ViewHolder {
        EditText et_dadosJogador_nome;
        EditText et_dadosJogador_telefone;
        EditText et_dadosJogador_njogos;
        Button bt_dadosJogador_editar;
        Button bt_dadosJogador_eliminar;
        Button bt_dadosJogador_cancelar;
    }
}

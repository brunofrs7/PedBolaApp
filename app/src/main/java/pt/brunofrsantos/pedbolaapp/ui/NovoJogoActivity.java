package pt.brunofrsantos.pedbolaapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import pt.brunofrsantos.pedbolaapp.R;

public class NovoJogoActivity extends AppCompatActivity {


    private ViewHolder viewHolder = new ViewHolder();
    ArrayList<Integer> listaJogosJogadores;
    ArrayList<String> listaNomesJogadores;
    ArrayList<String> listaIDsJogadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_jogo);
        listaJogosJogadores = new ArrayList<>();
        listaNomesJogadores = new ArrayList<>();
        listaIDsJogadores = new ArrayList<>();

        viewHolder.tv_registarJogo_jogadores = findViewById(R.id.tv_registarJogo_jogadores);
        viewHolder.bt_registarJogo_adicionarJogadores = findViewById(R.id.bt_registarJogo_adicionarJogadores);
        viewHolder.bt_registarJogo_registar = findViewById(R.id.bt_registarJogo_registar);

        if (viewHolder.tv_registarJogo_jogadores.getText().toString().equals("Sem jogadores selecionados"))
            viewHolder.bt_registarJogo_registar.setVisibility(View.INVISIBLE);
        else
            viewHolder.bt_registarJogo_registar.setVisibility(View.VISIBLE);

        viewHolder.bt_registarJogo_adicionarJogadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NovoJogoActivity.this, ListaJogadoresSelecionaveisActivity.class), 1);
            }
        });

        viewHolder.bt_registarJogo_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String jogadores10Jogos = "";
                for (int i = 0; i < listaIDsJogadores.size(); i++) {
                    int njogos = listaJogosJogadores.get(i) + 1;
                    if (njogos % 10 == 0)
                        jogadores10Jogos += listaNomesJogadores.get(i) + "\n";
                    db.collection("Jogador").document(listaIDsJogadores.get(i)).update(
                            "njogos", njogos);
                }

                if (!jogadores10Jogos.equals("")) {
                    new AlertDialog.Builder(NovoJogoActivity.this)
                            .setTitle("Mensagem")
                            .setMessage("Jogadores com jogo gratuito:\n" + jogadores10Jogos)

                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(NovoJogoActivity.this, "Jogo registado com sucesso", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                } else {
                    Toast.makeText(NovoJogoActivity.this, "Jogo registado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    private class ViewHolder {
        TextView tv_registarJogo_jogadores;
        Button bt_registarJogo_adicionarJogadores;
        Button bt_registarJogo_registar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            listaIDsJogadores = data.getStringArrayListExtra("listaIDs");
            listaNomesJogadores = data.getStringArrayListExtra("listaNomes");
            listaJogosJogadores = data.getIntegerArrayListExtra("listaJogos");

            if (listaNomesJogadores.size() > 0) {
                String nomes = "";
                for (int i = 0; i < listaNomesJogadores.size(); i++)
                    nomes += listaNomesJogadores.get(i) + "\n";

                viewHolder.tv_registarJogo_jogadores.setText(nomes);

                viewHolder.bt_registarJogo_registar.setVisibility(View.VISIBLE);
            }

        } else if (resultCode == 0) {
            finish();
        }
    }
}

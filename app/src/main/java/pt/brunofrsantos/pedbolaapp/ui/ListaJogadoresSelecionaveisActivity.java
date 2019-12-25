package pt.brunofrsantos.pedbolaapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.String;
import java.util.ArrayList;

import pt.brunofrsantos.pedbolaapp.adapter.JogadorSelAdapter;
import pt.brunofrsantos.pedbolaapp.R;
import pt.brunofrsantos.pedbolaapp.model.Jogador;


public class ListaJogadoresSelecionaveisActivity extends AppCompatActivity {
    private ViewHolder viewHolder = new ViewHolder();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference jogadorRef = db.collection("Jogador");

    private JogadorSelAdapter adapter;
    ArrayList<String> listaNomesJogadores;
    ArrayList<String> listaIDsJogadores;
    ArrayList<Integer> listaJogosJogadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_jogadores_selecionaveis);
        listaNomesJogadores = new ArrayList<>();
        listaIDsJogadores = new ArrayList<>();
        listaJogosJogadores = new ArrayList<>();

        viewHolder.rv_jogadoresselecionaveis = findViewById(R.id.rv_jogadoresselecionaveis);
        viewHolder.bt_jogadoresSel_ok = findViewById(R.id.bt_jogadoresSel_ok);
        viewHolder.bt_jogadoresSel_cancelar = findViewById(R.id.bt_jogadoresSel_cancelar);

        viewHolder.bt_jogadoresSel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                if (listaIDsJogadores.size() > 0) {

                    i.putStringArrayListExtra("listaIDs", listaIDsJogadores);
                    i.putStringArrayListExtra("listaNomes", listaNomesJogadores);
                    i.putIntegerArrayListExtra("listaJogos",listaJogosJogadores);
                    setResult(1,i);
                } else {
                    Toast.makeText(ListaJogadoresSelecionaveisActivity.this, "Não foram selecionados jogadores", Toast.LENGTH_SHORT).show();
                    setResult(0,i);
                }
                finish();
            }
        });

        viewHolder.bt_jogadoresSel_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = jogadorRef.orderBy("nome", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Jogador> options = new FirestoreRecyclerOptions.Builder<Jogador>()
                .setQuery(query, Jogador.class)
                .build();

        adapter = new JogadorSelAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.rv_jogadoresselecionaveis);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new JogadorSelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Jogador jogador = documentSnapshot.toObject(Jogador.class);
                String id = documentSnapshot.getId();
                int pos = existe(id);

                if (pos >= 0) {
                    listaIDsJogadores.remove(pos);
                    listaNomesJogadores.remove(pos);
                    listaJogosJogadores.remove(pos);
                } else {
                    listaJogosJogadores.add(jogador.getNjogos());
                    listaNomesJogadores.add(jogador.getNome());
                    listaIDsJogadores.add(id);
                }
            }
        });
    }

    public int existe(String id) {
        for (int i = 0; i < listaIDsJogadores.size(); i++) {
            if (listaIDsJogadores.get(i).equals(id))
                return i;
        }
        return -1;
    }

    private class ViewHolder {
        RecyclerView rv_jogadoresselecionaveis;
        Button bt_jogadoresSel_ok;
        Button bt_jogadoresSel_cancelar;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

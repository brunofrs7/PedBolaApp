package pt.brunofrsantos.pedbolaapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import pt.brunofrsantos.pedbolaapp.adapter.JogadorAdapter;
import pt.brunofrsantos.pedbolaapp.R;
import pt.brunofrsantos.pedbolaapp.model.Jogador;

public class VerJogadorActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference jogadorRef = db.collection("Jogador");

    private JogadorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_jogador);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = jogadorRef.orderBy("nome", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Jogador> options = new FirestoreRecyclerOptions.Builder<Jogador>()
                .setQuery(query, Jogador.class)
                .build();

        adapter = new JogadorAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.rv_verJogadores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new JogadorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Jogador jogador = documentSnapshot.toObject(Jogador.class);
                String id = documentSnapshot.getId();
                Intent i = new Intent(VerJogadorActivity.this, DadosJogadorActivity.class);
                i.putExtra("id", id);
                i.putExtra("nome", jogador.getNome());
                i.putExtra("telefone", jogador.getTelefone());
                i.putExtra("njogos", jogador.getNjogos());
                startActivity(i);
            }
        });
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

package pt.brunofrsantos.pedbolaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class JogadorAdapter extends FirestoreRecyclerAdapter<Jogador, JogadorAdapter.JogadorHolder> {

    private OnItemClickListener listener;

    public JogadorAdapter(@NonNull FirestoreRecyclerOptions<Jogador> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull JogadorHolder holder, int position, @NonNull Jogador model) {
        holder.tv_rowJogador_nome.setText(model.getNome());
        holder.tv_rowJogador_njogos.setText("Jogos: " + String.valueOf(model.getNjogos()));
        holder.tv_rowJogador_telefone.setText(model.getTelefone());
    }

    @NonNull
    @Override
    public JogadorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jogador_item, parent, false);
        return new JogadorHolder(v);
    }

    class JogadorHolder extends RecyclerView.ViewHolder {

        TextView tv_rowJogador_nome;
        TextView tv_rowJogador_telefone;
        TextView tv_rowJogador_njogos;

        public JogadorHolder(@NonNull View itemView) {
            super(itemView);
            tv_rowJogador_nome = itemView.findViewById(R.id.tv_rowJogador_nome);
            tv_rowJogador_telefone = itemView.findViewById(R.id.tv_rowJogador_telefone);
            tv_rowJogador_njogos = itemView.findViewById(R.id.tv_rowJogador_njogos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

package pt.brunofrsantos.pedbolaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import pt.brunofrsantos.pedbolaapp.R;
import pt.brunofrsantos.pedbolaapp.model.Jogador;

public class JogadorSelAdapter extends FirestoreRecyclerAdapter<Jogador, JogadorSelAdapter.JogadorSelHolder> {

    private OnItemClickListener listener;

    public JogadorSelAdapter(@NonNull FirestoreRecyclerOptions<Jogador> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull JogadorSelHolder holder, int position, @NonNull Jogador model) {
        holder.tv_rowJogador_nome.setText(model.getNome());
        holder.tv_rowJogador_njogos.setText("Jogos: " + String.valueOf(model.getNjogos()));
        holder.tv_rowJogador_telefone.setText(model.getTelefone());
    }

    @NonNull
    @Override
    public JogadorSelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jogador_sel_item, parent, false);
        return new JogadorSelHolder(v);
    }

    class JogadorSelHolder extends RecyclerView.ViewHolder {

        TextView tv_rowJogador_nome;
        TextView tv_rowJogador_telefone;
        TextView tv_rowJogador_njogos;
        CheckedTextView mCheckedTextView;


        public JogadorSelHolder(@NonNull View itemView) {
            super(itemView);
            tv_rowJogador_nome = itemView.findViewById(R.id.tv_rowJogador_nome);
            tv_rowJogador_telefone = itemView.findViewById(R.id.tv_rowJogador_telefone);
            tv_rowJogador_njogos = itemView.findViewById(R.id.tv_rowJogador_njogos);
            mCheckedTextView = itemView.findViewById(R.id.cb_rowJogadorSel);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        if (mCheckedTextView.isChecked())
                            mCheckedTextView.setChecked(false);
                        else
                            mCheckedTextView.setChecked(true);
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

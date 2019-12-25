package pt.brunofrsantos.pedbolaapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Jogador implements Parcelable {

    private String nome;
    private String telefone;
    private int njogos;

    public Jogador() {
        nome = "";
        telefone = "";
        njogos = 0;
    }

    public Jogador(String nome, String telefone, int njogos) {
        this.nome = nome;
        this.telefone = telefone;
        this.njogos = njogos;
    }

    public Jogador(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
        njogos = 0;
    }

    protected Jogador(Parcel in) {
        nome = in.readString();
        telefone = in.readString();
        njogos = in.readInt();
    }

    public static final Creator<Jogador> CREATOR = new Creator<Jogador>() {
        @Override
        public Jogador createFromParcel(Parcel in) {
            return new Jogador(in);
        }

        @Override
        public Jogador[] newArray(int size) {
            return new Jogador[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getNjogos() {
        return njogos;
    }

    public void setNjogos(int njogos) {
        this.njogos = njogos;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(telefone);
        dest.writeInt(njogos);
    }
}

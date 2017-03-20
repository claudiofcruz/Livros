package claudiocruz.fiap.com.br.livros.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by claudiocruz on 20/03/17.
 */

public class Livro implements Parcelable {

    // Properties
    public final static String NOME_TABELA  = "TB_LIVROS";
    public final static String TITLE  = "TITLE";
    public final static String AUTHOR  = "AUTOR";
    public final static String DESC  = "DESC";
    public final static String EDITOR  = "EDITOR";

    private String nome;
    private String desc;
    private String autor;
    private String editor;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }


    public Livro(String nome, String desc, String autor, String editor){
        setNome(nome);
        setDesc(desc);
        setAutor(autor);
        setEditor(editor);
    }

    protected Livro(Parcel in) {
        nome = in.readString();
        desc = in.readString();
        autor = in.readString();
        editor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(desc);
        dest.writeString(autor);
        dest.writeString(editor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Livro> CREATOR = new Creator<Livro>() {
        @Override
        public Livro createFromParcel(Parcel in) {
            return new Livro(in);
        }

        @Override
        public Livro[] newArray(int size) {
            return new Livro[size];
        }
    };
}

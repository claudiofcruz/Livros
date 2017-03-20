package claudiocruz.fiap.com.br.livros.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import claudiocruz.fiap.com.br.livros.model.Livro;

/**
 * Created by claudiocruz on 20/03/17.
 */

public class LivroDAO {

    private SQLiteDatabase db;

    public static String createTable() {
        return "CREATE TABLE " + Livro.NOME_TABELA + " ( "
                + Livro.TITLE + " TEXT primary key, "
                + Livro.DESC + " TEXT, "
                + Livro.AUTHOR + " TEXT, "
                + Livro.EDITOR + " text)";
    }

    public static String dropTable() {
        return "DROP TABLE IF EXISTS " + Livro.NOME_TABELA;
    }


    public boolean insereDado(Livro livro) {
        ContentValues valores;
        long resultado;

        db = DatabaseManager.getInstance().openDatabase(true);
        valores = new ContentValues();
        valores.put(Livro.TITLE, livro.getNome());
        valores.put(Livro.DESC, livro.getDesc());
        valores.put(Livro.AUTHOR, livro.getAutor());
        valores.put(Livro.EDITOR, livro.getEditor());
        resultado = db.insert(Livro.NOME_TABELA, null, valores);

        DatabaseManager.getInstance().closeDatabase();

        return resultado == -1 ? false : true;
    }

    public Cursor carregaDadoById(String nome){
        Cursor cursor;
        String[] campos =  {Livro.TITLE,Livro.DESC,Livro.AUTHOR,Livro.EDITOR};
        String where = Livro.TITLE + "= '" + nome + "'";
        db = DatabaseManager.getInstance().openDatabase(true);
        cursor = db.query(Livro.NOME_TABELA, campos,
                where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {Livro.TITLE,Livro.DESC,Livro.AUTHOR,Livro.EDITOR};
        db = DatabaseManager.getInstance().openDatabase(true);
        cursor = db.query(Livro.NOME_TABELA, campos,
                null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }


    public boolean alteraDados(Livro livro) {
        ContentValues valores;
        long resultado;

        db = DatabaseManager.getInstance().openDatabase(true);
        valores = new ContentValues();
        valores.put(Livro.TITLE, livro.getNome());
        valores.put(Livro.DESC, livro.getDesc());
        valores.put(Livro.AUTHOR, livro.getAutor());
        valores.put(Livro.EDITOR, livro.getEditor());
        String where = Livro.TITLE + "= '" + livro.getNome() + "'";

        resultado = db.update(Livro.NOME_TABELA,valores,where,null);

        DatabaseManager.getInstance().closeDatabase();

        return resultado == -1 ? false : true;
    }

    public boolean removeDados(Livro livro) {
        ContentValues valores;
        long resultado;

        db = DatabaseManager.getInstance().openDatabase(true);
        String where = Livro.TITLE + "= '" + livro.getNome() + "'";

        resultado = db.delete(Livro.NOME_TABELA,where,null);

        DatabaseManager.getInstance().closeDatabase();

        return resultado == -1 ? false : true;
    }
}

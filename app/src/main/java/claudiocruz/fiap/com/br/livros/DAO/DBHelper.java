package claudiocruz.fiap.com.br.livros.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import claudiocruz.fiap.com.br.livros.MinhaAplicacao;

/**
 * Created by claudiocruz on 19/03/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static String dataBaseName = "Livros.db";
    public static int  dataBaseVersion =  1;

    public DBHelper() {
        super(MinhaAplicacao.getContext(),
                dataBaseName,
                null,
                dataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.createTable());
        db.execSQL(LivroDAO.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserDAO.dropTable());
        db.execSQL(LivroDAO.dropTable());
        onCreate(db);
    }
}

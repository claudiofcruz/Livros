package claudiocruz.fiap.com.br.livros.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import claudiocruz.fiap.com.br.livros.model.User;

/**
 * Created by claudiocruz on 19/03/17.
 */

public class UserDAO {

    private SQLiteDatabase db;

    public static String createTable() {
        return "CREATE TABLE " + User.NOME_TABELA + " ( "
                + User.NAME + " TEXT primary key, "
                + User.PASSWORD + " text)";
    }

    public static String dropTable() {
        return "DROP TABLE IF EXISTS " + User.NOME_TABELA;
    }


    public String insereDado(User user) {
        ContentValues valores;
        long resultado;

        db = DatabaseManager.getInstance().openDatabase(true);
        valores = new ContentValues();
        valores.put(User.NAME, user.getName());
        valores.put(User.PASSWORD, user.getPassword());
        resultado = db.insert(User.NOME_TABELA, null, valores);

        DatabaseManager.getInstance().closeDatabase();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public Cursor carregaDadoById(String user){
        Cursor cursor;
        String[] campos =  {User.NAME,User.PASSWORD};
        String where = User.NAME + "= '" + user + "'";
        db = DatabaseManager.getInstance().openDatabase(true);
        cursor = db.query(User.NOME_TABELA, campos,
                where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public boolean isEmpty() {
        Cursor cursor;
        String[] campos =  {User.NAME,User.PASSWORD};
        db = DatabaseManager.getInstance().openDatabase(true);
        cursor = db.rawQuery("select * from '"+User.NOME_TABELA+"'", null);

        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                DatabaseManager.getInstance().closeDatabase();
                return false;
            }
            cursor.close();
        }
        DatabaseManager.getInstance().closeDatabase();
        return true;
    }

    public boolean isTableExists() {
        Cursor cursor;
        db = DatabaseManager.getInstance().openDatabase(true);
        cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+User.NOME_TABELA+"'", null);

        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                DatabaseManager.getInstance().closeDatabase();
                return true;
            }
            cursor.close();
        }
        DatabaseManager.getInstance().closeDatabase();
        return false;
    }


}

package claudiocruz.fiap.com.br.livros;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import claudiocruz.fiap.com.br.livros.DAO.DBHelper;
import claudiocruz.fiap.com.br.livros.DAO.DatabaseManager;


/**
 * Created by claudiocruz on 19/03/17.
 */

public class MinhaAplicacao extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        this.context = getApplicationContext();

        //Inicializa a base de dados
        DatabaseManager.initializeInstance(new DBHelper());
    }

    //Criado para uso do DBHElper (Sqlite)
    public static Context getContext(){
        return context;
    }

}

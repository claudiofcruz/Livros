package claudiocruz.fiap.com.br.livros;

/**
 * Created by claudiocruz on 18/03/17.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import claudiocruz.fiap.com.br.livros.DAO.UserDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import claudiocruz.fiap.com.br.livros.api.UserAPI;
import claudiocruz.fiap.com.br.livros.model.User;


public class SplashScreen extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback  {

    ImageView imgLogo;
    User user;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //For the first excution create database
        UserDAO userDAO = new UserDAO();
        userDAO.isTableExists();


            //Prepare ImgegeView to receive
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        imgLogo.setBackgroundResource(R.anim.bookshelf);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.INTERNET},1);

        } else {
            new JsonData().execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new JsonData().execute();
            }
            else
            {
                Toast.makeText(this, R.string.permissionnotallowed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class JsonData extends AsyncTask<Void, Void, Void>  implements Callback<User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create animation
            AnimationDrawable frameAnimation = (AnimationDrawable) imgLogo.getBackground();
            frameAnimation.start();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Retrofit retrofit =  new  Retrofit.Builder()
                    .baseUrl("http://www.mocky.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserAPI userAPI = retrofit.create(UserAPI.class);
            Call<User> call = userAPI.findBy();

            call.enqueue(this);
            return null;
        }

        @Override
        public void onResponse(Call<User> call, Response<User> response) {

            User user = response.body();

            UserDAO userDAO = new UserDAO();
            if (userDAO.isEmpty()) {
                userDAO.insereDado(user);
            }

            SharedPreferences sp = getSharedPreferences("PREFERENCES",MODE_PRIVATE);
            if(sp.getBoolean("keepConnected", false)){
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
            }

        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

            Log.e("JsonData", t.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), R.string.jsongetdata, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

}


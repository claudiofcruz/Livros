package claudiocruz.fiap.com.br.livros;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import claudiocruz.fiap.com.br.livros.DAO.UserDAO;
import claudiocruz.fiap.com.br.livros.model.User;

public class LoginActivity extends AppCompatActivity {

    EditText edUserName, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassword);
    }

    public void onClick(View v) {
        if (edUserName.getText().length() > 0  && edPassword.getText().length() > 0) {

            UserDAO userDAO = new UserDAO();

            String user = edUserName.getText().toString();

            Cursor queryResult = userDAO.carregaDadoById(user);
            if (queryResult.getCount() > 0) {

                if (queryResult.getString( queryResult.getColumnIndex(User.PASSWORD) )
                        .equals(edPassword.getText().toString())) {

                    CheckBox cbPreference = (CheckBox) findViewById(R.id.cb_keep);
                    if (cbPreference.isChecked()) {
                        savePreferences();
                    }

                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getBaseContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(this, R.string.login_invalid, Toast.LENGTH_LONG).show();
            }

        } else {

            Toast.makeText(this, R.string.login_fillfields, Toast.LENGTH_LONG).show();
        }
    }

    private void savePreferences() {
        SharedPreferences sp =
                getSharedPreferences("PREFERENCES",
                        MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("keepConnected", true);
        editor.commit();
    }

}

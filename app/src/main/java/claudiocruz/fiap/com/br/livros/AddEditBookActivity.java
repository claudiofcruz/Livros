package claudiocruz.fiap.com.br.livros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import claudiocruz.fiap.com.br.livros.DAO.LivroDAO;
import claudiocruz.fiap.com.br.livros.model.Livro;

public class AddEditBookActivity extends AppCompatActivity {

    TextView tvTitle;
    EditText edBookName, edBookAuthor, edBookDesc, edBookEditor;
    Button btn_save;
    boolean isNewBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        isNewBook = getIntent().getBooleanExtra("newBook", false);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        edBookName = (EditText) findViewById(R.id.edBookName);
        edBookAuthor = (EditText) findViewById(R.id.edBookAuthor);
        edBookDesc = (EditText) findViewById(R.id.edBookDesc);
        edBookEditor = (EditText) findViewById(R.id.edBookEditor);

        btn_save = (Button) findViewById(R.id.btn_save);

        if ( isNewBook ) {
            tvTitle.setText(R.string.add_new_book);
            btn_save.setText(R.string.book_add);
        } else {
            tvTitle.setText(R.string.update_book);
            btn_save.setText(R.string.book_update);

            Livro livro = getIntent().getParcelableExtra("livro");
            edBookName.setText(livro.getNome());
            edBookDesc.setText(livro.getDesc());
            edBookAuthor.setText(livro.getAutor());
            edBookEditor.setText(livro.getEditor());
        }

    }

    public void AddEditButtonAction(View view){

        if( !edBookName.getText().toString().isEmpty()  &&
                !edBookDesc.getText().toString().isEmpty() &&
                !edBookAuthor.getText().toString().isEmpty() &&
                !edBookEditor.getText().toString().isEmpty() ) {

            Livro livro = new Livro(
                    edBookName.getText().toString(),
                    edBookDesc.getText().toString(),
                    edBookAuthor.getText().toString(),
                    edBookEditor.getText().toString());

            LivroDAO dao = new LivroDAO();

            if(isNewBook){
                if (dao.insereDado(livro)) {
                    Toast.makeText(this,R.string.book_insert_success,Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,R.string.book_insert_failed,Toast.LENGTH_LONG).show();
                }
            } else {
                if (dao.alteraDados(livro)) {
                    Toast.makeText(this,R.string.book_update_success,Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this,R.string.book_update_failed,Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this,R.string.all_required,Toast.LENGTH_LONG).show();
        }
    }

}

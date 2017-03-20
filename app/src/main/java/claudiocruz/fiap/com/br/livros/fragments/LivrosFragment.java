package claudiocruz.fiap.com.br.livros.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import claudiocruz.fiap.com.br.livros.AddEditBookActivity;
import claudiocruz.fiap.com.br.livros.DAO.LivroDAO;
import claudiocruz.fiap.com.br.livros.R;
import claudiocruz.fiap.com.br.livros.adapter.LivroListAdapter;
import claudiocruz.fiap.com.br.livros.listener.OnClickListener;
import claudiocruz.fiap.com.br.livros.listener.OnLongClickListener;
import claudiocruz.fiap.com.br.livros.model.Livro;


public class LivrosFragment extends Fragment {

    private RecyclerView rvLivros;
    private LivroListAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public LivrosFragment() {
        // Required empty public constructor
    }

    public static LivrosFragment newInstance(String param1, String param2) {
        LivrosFragment fragment = new LivrosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLivros();
    }

    private void loadLivros() {
        //Chamada ao bando de dados

        LivroDAO dao = new LivroDAO();
        Cursor cursor = dao.carregaDados();

        List<Livro> livros = new ArrayList<Livro>();
        Livro livro;

        while(!cursor.isAfterLast()) {
            livro = new Livro(cursor.getString(cursor.getColumnIndex("TITLE")),
                            cursor.getString(cursor.getColumnIndex("DESC")),
                            cursor.getString(cursor.getColumnIndex("AUTOR")),
                                    cursor.getString(cursor.getColumnIndex("EDITOR")));

            livros.add(livro);
            cursor.moveToNext();
        }
        cursor.close();


        adapter = new LivroListAdapter(getContext(), livros, onClickListener(), onLongClickListener());
        rvLivros.setAdapter(adapter);

    }

    private OnClickListener onClickListener () {
        return new OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent i = new Intent(v.getContext(), AddEditBookActivity.class);
                i.putExtra("newBook",false);
                i.putExtra("livro",adapter.getItem(position));
                startActivity(i);
            }
        };

    }

    private OnLongClickListener onLongClickListener () {
        return new OnLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {

                final Livro livroToDelete = adapter.getItem(position);

                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.delete_entry) + livroToDelete.getNome())
                        .setMessage(R.string.confirm_delete_entry)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LivroDAO dao  = new LivroDAO();
                                if (dao.removeDados(livroToDelete)){
                                    Toast.makeText(getContext(),getString(R.string.entry_deleted),Toast.LENGTH_SHORT).show();
                                    loadLivros();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_livros, container, false);
        rvLivros = (RecyclerView) v.findViewById(R.id.recyclerView);
        rvLivros.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Visual de como os itens são adicionados na lsita
        rvLivros.setItemAnimator(new DefaultItemAnimator());
        rvLivros.setHasFixedSize(true);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLivros();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

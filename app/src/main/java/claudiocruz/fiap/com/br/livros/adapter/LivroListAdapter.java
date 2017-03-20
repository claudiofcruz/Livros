package claudiocruz.fiap.com.br.livros.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import claudiocruz.fiap.com.br.livros.listener.OnClickListener;
import claudiocruz.fiap.com.br.livros.listener.OnLongClickListener;
import claudiocruz.fiap.com.br.livros.model.Livro;
import claudiocruz.fiap.com.br.livros.R;

public class LivroListAdapter extends
        RecyclerView.Adapter<LivroListAdapter.LivrosViewHolder>{

    private Context context;
    private List<Livro> livros;
    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;
    private LivrosViewHolder mHolder;

    public LivroListAdapter(Context context
            , List<Livro> livros
            , OnClickListener clickListener
            , OnLongClickListener longClickListener)
    {
        this.context = context;
        this.livros = livros;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public LivrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context)
                .inflate(R.layout.item_livro, parent, false);

        mHolder = new LivrosViewHolder(v);

        return new LivrosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LivrosViewHolder holder, final int position) {

        mHolder.tvName.setText(livros.get(position).getNome());
        mHolder.tvDesc.setText(livros.get(position).getDesc());

        if(clickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    clickListener.onClick(mHolder.itemView, position);
                }
            });
        }


        if(longClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onLongClick(mHolder.itemView, position);
                    return false;
                }
            });
        }
    }

    //classe interna utilizada somente pelo este adapter
    public static class LivrosViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDesc;

        public LivrosViewHolder(View itemView) {
            super(itemView);

            tvName      = (TextView) itemView.findViewById(R.id.tvName);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
        }
    }


    public Livro getItem(int position){
        return livros.get(position);
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

}

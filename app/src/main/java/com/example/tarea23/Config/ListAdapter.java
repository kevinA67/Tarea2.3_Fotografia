package com.example.tarea23.Config;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.tarea23.R;
import java.util.List;

import Models.Images;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Images> datos;
    private LayoutInflater inflater;
    private Context context;

    public ListAdapter(List<Images> datos, Context context) {
        this.inflater=LayoutInflater.from(context);
        this.datos = datos;
        this.context = context;
    }

    @Override
    public int getItemCount(){return datos.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
        View view=inflater.inflate(R.layout.disenio,null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(datos.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, description;
        ImageView imageView;

        ViewHolder(View itemView){
            super(itemView);
            id=(TextView) itemView.findViewById(R.id.txtId);
            description=(TextView) itemView.findViewById(R.id.txtDescription2);
            imageView=(ImageView) itemView.findViewById(R.id.imageView2);
        }
        void bindData(final Images images){
            id.setText("Id: "+images.getId());
            description.setText("Descripción: "+images.getDescription());
            imageView.setImageBitmap(images.getImage());
        }
    }
}

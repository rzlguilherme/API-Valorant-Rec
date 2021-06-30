package com.example.apivalorant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Agente> {

    private ArrayList<Agente> Lista_Agente;

    public ListAdapter(@NonNull Context context, int resource, ArrayList<Agente> Lista_Agente){
        super(context, resource, Lista_Agente);
        this.Lista_Agente = Lista_Agente;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itensagente,
                    parent, false);
        }

        ImageView agenteImage = convertView.findViewById(R.id.imgAgente);
        TextView agenteNome = convertView.findViewById(R.id.txtNome);
        TextView agenteCod = convertView.findViewById(R.id.txtCod);

        Picasso.get().load(Lista_Agente.get(position).getFotoAgente()).into(agenteImage);
        agenteNome.setText(Lista_Agente.get(position).getNomeAgente());
        agenteCod.setText(Lista_Agente.get(position).getCodAgente());

        return convertView;
    }
}

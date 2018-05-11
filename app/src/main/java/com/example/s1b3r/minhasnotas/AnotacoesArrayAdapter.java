package com.example.s1b3r.minhasnotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by S1B3R on 16/12/2017.
 */

public class AnotacoesArrayAdapter extends ArrayAdapter<Anotacao> {

    public AnotacoesArrayAdapter(Context context, int textViewResourceId, ArrayList<Anotacao> items) {
        super(context, textViewResourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        Anotacao novaNota = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nota, parent, false);
        }
        // Lookup view for data population
        TextView data = (TextView) convertView.findViewById(R.id.DataCriacao);
        TextView titulo = (TextView) convertView.findViewById(R.id.Titulo);
        TextView texto = (TextView) convertView.findViewById(R.id.Resumo);
        TextView idSQL = (TextView) convertView.findViewById(R.id.IdSQL);
        // Populate the data into the template view using the data object
        data.setText(novaNota.getDataCriacao().toString());
        titulo.setText(novaNota.getTitulo());
        texto.setText(novaNota.getTexto());
        idSQL.setText(String.valueOf(novaNota.getId()));
        // Return the completed view to render on screen
        return convertView;
    }

}

package com.example.developer10.newexample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developer10.newexample.Models.Character;

import java.util.ArrayList;
import java.util.List;

public class DatosAdapter extends RecyclerView.Adapter<DatosAdapter.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<String> listQuotes;
    private View.OnClickListener listener;
    private int posicion;
    private List<Character> cha;

    public DatosAdapter(ArrayList<String> quotes) {
        this.listQuotes = quotes;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null, false);
        //view.setOnClickListener(this);
        return new ViewHolderDatos(view);

    }

    public void fillObject(List<Character> c)
    {
        this.cha = c;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDatos viewHolderDatos, final int i) {
        viewHolderDatos.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), CharacterActivity.class);
                intent.putExtra("name", cha.get(i).getCharacter());
                intent.putExtra("url", cha.get(i).getImage());
                view.getContext().startActivity(intent); //If you are inside activity, otherwise pass context to this funtion
            }
        });
        viewHolderDatos.asignarDatos(listQuotes.get(i));
    }

    @Override
    public int getItemCount() {
        return listQuotes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){

            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView quoteCharac;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            quoteCharac = itemView.findViewById(R.id.quoteCharacter);
        }

        public void asignarDatos(String frase) {
            quoteCharac.setText(frase);
        }


    }
}

package com.parse.starter.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostagemAdapter extends ArrayAdapter<ParseObject>{

    private ArrayList<ParseObject> postagens ;
    private Context context;

    public PostagemAdapter(Context c , ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.postagens = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        //Verifica se não existe o objeto view criado, pois a view utilizada é armazenado no cache do android e fica na variável convertView

        if(view == null){
            //Inicializar objeto para montage do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a view a partir do xml
            view = inflater.inflate(R.layout.lista_postagem,parent,false);
        }

        //Veridica se existe postagem
        if(postagens.size()>0){

            //recupera componentes da tela
            ImageView imagemPostagem = (ImageView) view.findViewById(R.id.image_lista_postagem);
            ParseObject parseObject = postagens.get(position);
            Picasso.with(this.context).load(parseObject.getParseFile("imagem").getUrl()).fit().into(imagemPostagem);

        }

        return view;

    }
}
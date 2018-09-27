package com.parse.starter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;
import com.parse.starter.adapter.PostagemAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String userName;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        //Recupera dados enviados da intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");


        //Configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_feed_usuario);
        toolbar.setTitle(userName);
        toolbar.setTitleTextColor(R.color.preto);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);


        postagens = new ArrayList<>();
        listView =(ListView) findViewById(R.id.list_feed_usuario);
        adapter = new PostagemAdapter(getApplicationContext(),postagens);
        listView.setAdapter(adapter);

        getPostagens();

    }

    private void getPostagens(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Imagem");
        query.whereEqualTo("username",userName);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){

                    if (objects.size()>0){
                        postagens.clear();
                        for (ParseObject parseObject: objects){
                            postagens.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();

                    }

                }else{
                    e.printStackTrace();
                }
            }
        });

    }

}

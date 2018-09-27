package whatsappandroid.cursoandroid.com.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import whatsappandroid.cursoandroid.com.whatsapp.Config.ConfiguracaoFirebase;
import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.adapter.TabAdapter;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsappandroid.cursoandroid.com.whatsapp.helper.SlidingTabLayout;
import whatsappandroid.cursoandroid.com.whatsapp.model.Contato;
import whatsappandroid.cursoandroid.com.whatsapp.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth autenticacao;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    private String identificadorContato;

    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //Configurar Sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccente));

        //Configurar Adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.item_add:
                abrirCadastroContato();
                return true;
            case R.id.item_pesquisa:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deslogarUsuario(){
        autenticacao.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirCadastroContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //Configurar Dialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("Digite o email do Usuário:");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);

        alertDialog.setView(editText);

        //Configurar botoes
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailContato = editText.getText().toString();
                if (emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this,"Preencha o email.", Toast.LENGTH_LONG).show();
                }else{

                    //Verificar se o usuário já esta cadastrado no nosso App
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //Recuperar instancia Firebase

                    firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.getValue() != null){

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);

                                //Recuperar identificador usuario logado(base64)
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFirebase.getFirebase();
                                firebase = firebase.child("contatos")
                                                    .child(identificadorUsuarioLogado)
                                                    .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                firebase.setValue(contato);
                                Toast.makeText(MainActivity.this,"Contato cadastrado com sucesso",Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(MainActivity.this,"Usuário não possui cadastro",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }
}

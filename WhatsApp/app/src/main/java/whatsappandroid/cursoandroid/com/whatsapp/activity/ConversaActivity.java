package whatsappandroid.cursoandroid.com.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import whatsappandroid.cursoandroid.com.whatsapp.Config.ConfiguracaoFirebase;
import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.adapter.MensagemAdapter;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsappandroid.cursoandroid.com.whatsapp.model.Conversa;
import whatsappandroid.cursoandroid.com.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton btEnviar;
    private EditText editMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    //dados destinatario
    private String nomeDestinatario;
    private String idDestinatario;

    //dados remetente
    private String nomeRemetente;
    private String idRemetente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        btEnviar = (ImageButton) findViewById(R.id.bt_enviar);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        listView = (ListView) findViewById(R.id.lv_conversas);

        //Recuperar usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idRemetente = preferencias.getIdentificador();
        nomeRemetente = preferencias.getNome();
        Log.i("NOMERemetente",nomeRemetente);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            nomeDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            idDestinatario = Base64Custom.codificarBase64(emailDestinatario);
        }

        //Configurar toolbar
        toolbar.setTitle(nomeDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Monta listview e adaoter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this,mensagens);


        /*adapter = new ArrayAdapter(
                ConversaActivity.this,
                android.R.layout.simple_list_item_1,
                mensagens
        );*/
        listView.setAdapter(adapter);

        //Recuperar mensagem do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("mensagens")
                .child(idRemetente)
                .child(idDestinatario);

        //Cria listener mesagens
        valueEventListenerMensagem = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mensagens.clear();

                for (DataSnapshot dado: dataSnapshot.getChildren()){
                    Mensagem mensagem = dado.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMensagem);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoMensagem = editMensagem.getText().toString();
                if(textoMensagem.isEmpty()){
                    Toast.makeText(ConversaActivity.this,"Digite uma mensagem para enviar!",Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idRemetente);
                    mensagem.setMensagem(textoMensagem);

                    //Salvar hora do envio
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    Date hora = Calendar.getInstance().getTime();
                    String dataFormatada = sdf.format(hora);
                    mensagem.setHora(dataFormatada);

                    //Salvar mensagens
                    Boolean retornoMensagem = salvarMensagem(mensagem,idDestinatario);
                    if(!retornoMensagem) {
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar menasgem, tente novamente!", Toast.LENGTH_LONG).show();
                    }

                    //Salvar Conversas
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idDestinatario);
                    conversa.setNome(nomeDestinatario);
                    conversa.setMensagem(textoMensagem);
                    Boolean retornoConversaRemetente = salvarConversa(idRemetente,idDestinatario, conversa);
                    if(!retornoConversaRemetente) {
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente!", Toast.LENGTH_LONG).show();
                    }else{
                        //Salvar conversa para destinatario
                        conversa = new Conversa();
                        conversa.setIdUsuario(idRemetente);
                        conversa.setNome(nomeRemetente);
                        conversa.setMensagem(textoMensagem);
                        Boolean retornoConversaDestinatario = salvarConversa(idDestinatario,idRemetente, conversa);
                        if(!retornoConversaDestinatario)
                            Toast.makeText(ConversaActivity.this,"Problema ao salvar conversa para destinatario, tente novamente!",Toast.LENGTH_LONG).show();

                    }


                    editMensagem.setText("");
                }

            }
        });

    }

    private boolean salvarMensagem(Mensagem mensagem, String idUsuarioDestinatario){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");

            firebase.child(mensagem.getIdUsuario())
                    .child(idUsuarioDestinatario)
                    .push().setValue(mensagem);

            firebase.child(idUsuarioDestinatario)
                    .child(mensagem.getIdUsuario())
                    .push().setValue(mensagem);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa(String idUsuarioRemetente, String idUsuarioDestinatario, Conversa conversa){
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("conversas");
            firebase.child(idUsuarioRemetente)
                    .child(idUsuarioDestinatario)
                    .setValue(conversa);

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerMensagem);
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}

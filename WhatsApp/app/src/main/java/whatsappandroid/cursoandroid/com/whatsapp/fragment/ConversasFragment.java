package whatsappandroid.cursoandroid.com.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsappandroid.cursoandroid.com.whatsapp.Config.ConfiguracaoFirebase;
import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.activity.ConversaActivity;
import whatsappandroid.cursoandroid.com.whatsapp.adapter.ConversaAdapter;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsappandroid.cursoandroid.com.whatsapp.model.Contato;
import whatsappandroid.cursoandroid.com.whatsapp.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerConversas;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        listView = (ListView) view.findViewById(R.id.lv_conversas_ID);

        conversas = new ArrayList<>();
        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);

        //Recuperar dados do usuario logado
        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuarioLogado = preferencias.getIdentificador();

        //Recuperar conversas do Firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("conversas")
                .child(idUsuarioLogado);

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                conversas.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add(conversa);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //Recupera dados a serem passados
                Conversa conversa = conversas.get(position);

                //Enviando dados para conversa activity
                intent.putExtra("nome",conversa.getNome());
                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
                Log.i("IDUSUARIO",conversa.getIdUsuario());
                Log.i("IDUSUARIO",email);
                intent.putExtra("email",email);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversas);
    }
}

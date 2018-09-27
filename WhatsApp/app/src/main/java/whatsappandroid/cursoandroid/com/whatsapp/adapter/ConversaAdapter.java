package whatsappandroid.cursoandroid.com.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.model.Contato;
import whatsappandroid.cursoandroid.com.whatsapp.model.Conversa;

public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.conversas = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(conversas != null){
            //inicialiar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta view a partir do xml
            view = inflater.inflate(R.layout.lista_conversa, parent, false);

            //Recupera elemento para exibicao
            TextView nome = (TextView) view.findViewById(R.id.tv_conversas_nome);
            TextView ultimaMensagem = (TextView) view.findViewById(R.id.tv_conversas_mensagem);

            Conversa conversa = conversas.get(position);

            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());
        }

        return view;
    }
}
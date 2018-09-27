package whatsappandroid.cursoandroid.com.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsappandroid.cursoandroid.com.whatsapp.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context c, ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        //Verifica se a lista esta preenchida
        if(mensagens != null){

            //Recuperar dados do usuario remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetende = preferencias.getIdentificador();

            //Inicializa objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            Mensagem mensagem = mensagens.get(position);

            //Monta a view a partir do xml
            if (idUsuarioRemetende.equals(mensagem.getIdUsuario())){
                view = inflater.inflate(R.layout.item_mensagem_direita,parent,false);
                //Recupera elemento para exibição
                TextView textoMensagem = (TextView) view.findViewById(R.id.tv_mensagem_direita);
                TextView horaMensagem = (TextView) view.findViewById(R.id.tv_hora_direita);
                textoMensagem.setText(mensagem.getMensagem());
                horaMensagem.setText(mensagem.getHora());
            }else{
                view = inflater.inflate(R.layout.item_mensagem_esquerda,parent,false);
                //Recupera elemento para exibição
                TextView textoMensagem = (TextView) view.findViewById(R.id.tv_mensagem_esquerda);
                TextView horaMensagem = (TextView) view.findViewById(R.id.tv_hora_esquerda);
                textoMensagem.setText(mensagem.getMensagem());
                horaMensagem.setText(mensagem.getHora());
            }
        }

        return view;
    }
}
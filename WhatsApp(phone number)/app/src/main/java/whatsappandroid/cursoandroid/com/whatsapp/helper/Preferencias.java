package whatsappandroid.cursoandroid.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "whatsapp.preferencias";
    private int MODE = Context.MODE_PRIVATE;// mesma coisa que colocar zero
    private SharedPreferences.Editor editor;

    private String CHAVE_NOME = "nome";
    private String CHAVE_TELEFONE = "telefone";

    public Preferencias(Context contextoParametro){
        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO,MODE);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone){
        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_TELEFONE,telefone);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME,null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE,null));

        return dadosUsuario;
    }
}
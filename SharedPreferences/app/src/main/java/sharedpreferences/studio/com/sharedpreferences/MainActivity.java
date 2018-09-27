package sharedpreferences.studio.com.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nomeEscrito;
    private TextView nome;
    private Button botao;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEscrito = (EditText) findViewById(R.id.nomeID);
        nome = (TextView) findViewById(R.id.nomeSalvoID);
        botao = (Button) findViewById(R.id.buttonID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(nomeEscrito.getText().toString().equals("")){
                    Toast.makeText(getApplication(),"Por favor, preencher o nome.", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("nome", nomeEscrito.getText().toString());
                    editor.commit();
                    nome.setText("Olá, "+nomeEscrito.getText().toString());
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        if(sharedPreferences.contains("nome")){
            String nomeUsuario = sharedPreferences.getString("nome","usuário não definido");
            nome.setText("Olá, "+nomeUsuario);
        }else{
            nome.setText("Olá, usuário não definido");
        }
    }
}

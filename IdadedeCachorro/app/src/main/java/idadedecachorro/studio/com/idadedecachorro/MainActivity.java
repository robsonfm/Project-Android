package idadedecachorro.studio.com.idadedecachorro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button botaoIdade;
    private EditText caixaTexto;
    private TextView resultadoIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoIdade = (Button) findViewById(R.id.botaoDescobrirIdadeID);
        caixaTexto = (EditText) findViewById(R.id.caixaTextoID);
        resultadoIdade = (TextView) findViewById(R.id.resultadoIdadeID);

        botaoIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = caixaTexto.getText().toString();

                if (!textoDigitado.isEmpty()){
                    int valorDigitado = Integer.parseInt(textoDigitado);
                    int idade = 7 * valorDigitado;
                    resultadoIdade.setText("A idade do cachorro em anos humanos é: "+idade+" anos.");
                }else{
                    //Mensagem de Erro
                    resultadoIdade.setText("Não foi digitado nenhuma idade.");
                }
            }
        });
    }
}

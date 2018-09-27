package gasolinaoualcool.studio.com.gasolinaoualcool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textoResultado;
    private Button botaoVerificar;
    private EditText precoAlcool;
    private EditText preçoGasolina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoResultado = (TextView) findViewById(R.id.resultadoTextoID);
        botaoVerificar = (Button) findViewById(R.id.verificarBotaoID);
        preçoGasolina = (EditText) findViewById(R.id.valorGasolinaID);
        precoAlcool = (EditText) findViewById(R.id.valorAlcoolID);

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pGasolina = preçoGasolina.getText().toString();
                String pAlcool = precoAlcool.getText().toString();

                if(!pAlcool.isEmpty() && !pGasolina.isEmpty() && !pAlcool.equals("") && !pGasolina.equals("")){
                    double alcool = Double.parseDouble(pAlcool);
                    double gasolina = Double.parseDouble(pGasolina);

                    double porcentagem = alcool / gasolina;
                    porcentagem = Double.valueOf(String.format(Locale.US, "%.2f", porcentagem));

                    if (porcentagem < 0.7){
                        textoResultado.setText("Paridade: "+porcentagem+
                                                "\nÉ Melhor usar Alcool.");
                    }else{
                        textoResultado.setText("Paridade: "+porcentagem+
                                "\nÉ Melhor usar Gasolina.");
                    }


                }else{
                    Toast.makeText(MainActivity.this, "Preencha os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package frasedodia.studio.com.frasedodia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button botaoNovaFrase;
    private TextView textoNovaFrase;
    private String[] frases = {
            "Se você traçar metas absurdamente altas e falhar, seu fracasso será muito melhor que o sucesso de todos!",
            "O sucesso normalmente vem para quem está ocupado demais para procurar por ele!",
            "Se voce não está disposto a arriscar, esteja disposto a uma vida comum!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNovaFrase = (TextView) findViewById(R.id.textoNovaFraseID);
        botaoNovaFrase = (Button) findViewById(R.id.botaoNovaFraseID);

        botaoNovaFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random randomico = new Random();
                int numeroAleatorio = randomico.nextInt(frases.length);

                textoNovaFrase.setText(frases[numeroAleatorio]);

            }
        });

    }
}

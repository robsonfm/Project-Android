package caraoucoroa.studio.com.caraoucoroa;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DetalheActivity extends AppCompatActivity {

    private ImageView resultado;
    private ImageView botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        resultado = (ImageView) findViewById(R.id.imagemResultadoID);
        botaoVoltar = (ImageView) findViewById(R.id.botaoVoltarID);

        Bundle opcao = getIntent().getExtras();

        if(opcao != null){
            String opcaoRecuperada = opcao.getString("resultado");

            if(opcaoRecuperada.equals("cara")){
                resultado.setImageDrawable(ContextCompat.getDrawable(DetalheActivity.this,R.drawable.moeda_cara));
            }else{
                resultado.setImageDrawable(ContextCompat.getDrawable(DetalheActivity.this, R.drawable.moeda_coroa));
            }

        }

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetalheActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

package corpresonalizada.studio.com.corpersonalizada;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button botao;
    private RadioButton radioButtonSelecionado;
    private RelativeLayout layout;

    private static final String PREFERENCIA = "Preferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
        botao = (Button) findViewById(R.id.buttonID);
        layout = (RelativeLayout) findViewById(R.id.backgroundID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadioButtonSelecionado = radioGroup.getCheckedRadioButtonId();

                if (idRadioButtonSelecionado > 0){
                    radioButtonSelecionado = (RadioButton) findViewById(idRadioButtonSelecionado);

                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA,0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("corEscolhida", radioButtonSelecionado.getText().toString());
                    editor.commit();

                    setBackground(radioButtonSelecionado.getText().toString());
                }

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA,0);
        if(sharedPreferences.contains("corEscolhida")){
            String corRecuperada = sharedPreferences.getString("corEscolhida","Laranja");
            setBackground(corRecuperada);
        }

    }

    private void setBackground(String cor){
        if (cor.equals("Azul")){
            layout.setBackgroundColor(Color.parseColor("#495b7c"));
        }else if(cor.equals("Laranja")){
            layout.setBackgroundColor(Color.parseColor("#ee5109"));
        }else if (cor.equals("Verde")){
            layout.setBackgroundColor(Color.parseColor("#009670"));
        }
    }

}

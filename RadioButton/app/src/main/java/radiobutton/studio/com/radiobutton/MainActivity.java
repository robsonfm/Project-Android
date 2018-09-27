package radiobutton.studio.com.radiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioEscolhido;
    private Button botao;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroupID);
        botao = (Button) findViewById(R.id.buttonID);
        resultado = (TextView) findViewById(R.id.resultadoID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();

                if (idRadioButtonEscolhido > 0){
                    radioEscolhido = (RadioButton) findViewById(idRadioButtonEscolhido);
                    resultado.setText(radioEscolhido.getText());

                }

            }
        });
    }
}

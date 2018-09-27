package checkbox.studio.com.checkbox;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private CheckBox checkBoxCao;
    private CheckBox checkBoxGato;
    private CheckBox checkBoxPapaguaio;
    private CheckBox checkBoxPeixe;
    private CheckBox checkBoxIguana;
    private TextView exibicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxCao = (CheckBox) findViewById(R.id.checkBoxCaoID);
        checkBoxGato = (CheckBox) findViewById(R.id.checkBoxGatoID);
        checkBoxPapaguaio = (CheckBox) findViewById(R.id.checkBoxPapaguaioID);
        checkBoxPeixe = (CheckBox) findViewById(R.id.checkBoxPeixeID);
        checkBoxIguana = (CheckBox) findViewById(R.id.checkBoxIguanaID);
        botao = (Button) findViewById(R.id.botaoID);
        exibicao = (TextView) findViewById(R.id.exibicaoID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selecionados = "";

                selecionados = "Item: " +checkBoxCao.getText()+" :"+checkBoxCao.isChecked()+"\n";
                selecionados += "Item: " +checkBoxGato.getText()+" :"+checkBoxGato.isChecked()+"\n";
                selecionados += "Item: " +checkBoxPapaguaio.getText()+" :"+checkBoxPapaguaio.isChecked()+"\n";
                selecionados += "Item: " +checkBoxPeixe.getText()+" :"+checkBoxPeixe.isChecked()+"\n";
                selecionados += "Item: " +checkBoxIguana.getText()+" :"+checkBoxIguana.isChecked()+"\n";

                exibicao.setText(selecionados);


            }
        });
    }
}

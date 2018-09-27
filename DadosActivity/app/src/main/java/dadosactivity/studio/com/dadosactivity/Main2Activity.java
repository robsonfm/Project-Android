package dadosactivity.studio.com.dadosactivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends Activity {

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        texto = (TextView) findViewById(R.id.textoID);

        Bundle extra = getIntent().getExtras();

        if(extra != null){
            String textoPassado = extra.getString("nome");
            texto.setText(textoPassado);
        }


    }
}

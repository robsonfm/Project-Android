package signos.studio.com.signos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listaSignos;
    private String[] signos = {
            "Áries", "Touro", "Gêmeos", "Câncer", "Leão", "Virgem",
            "Libra", "Escorpião", "Sagitário", "Capricórnio", "Áquario",
            "Peixes"
    };

    private String[] perfis = {
            "Arianos são animados, independentes, individualistas, dinâmicos, corajosos e aventureiros.",
            "Taurinos são positivos, pacientes, determinados, noturnos, leais e românticos.",
            "Geminianos são positivos, mutáveis, racionais e bastante voláteis.",
            "Cancerianos são emotivos, possuem grande afeição e imaginação.",
            "Leoninos são generosos, nobres e criativos.",
            "Virginianos são perfeccionistas, inteligentes e gostam de opinar.",
            "Librianos são vaidosos, gentis e idealizadores.",
            "Escorpianos são intensos e misteriosos.",
            "Sagitarianos são originais, intuitivos e inspiradores.",
            "Capricornianos são responsavéis, persistentes e esforçados.",
            "Aquarianos são inovadores, originais e visionários.",
            "Piscinianos são solidarios, sensíveis e intuitivos."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSignos = (ListView) findViewById(R.id.listViewID);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1,signos);

        listaSignos.setAdapter(adaptador);

        listaSignos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int codigoPosição = position;
                Toast.makeText(getApplicationContext(),perfis[position],Toast.LENGTH_LONG).show();
            }
        });
    }
}

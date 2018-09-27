package projetofragmento.cursoandroid.com.projetofragmento;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botaoLogar;
    private Button botaoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoLogar = (Button) findViewById(R.id.bt_logar_id);
        botaoCadastro = (Button) findViewById(R.id.bt_cadastro);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                LoginFragment loginFragment = new LoginFragment();

                fragmentTransaction.add(R.id.id_conteiner_fragmento,loginFragment);

                fragmentTransaction.commit();
            }
        });

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CadastroFragment cadastroFragment = new CadastroFragment();

                fragmentTransaction.add(R.id.id_conteiner_fragmento,cadastroFragment);

                fragmentTransaction.commit();
            }
        });
    }
}

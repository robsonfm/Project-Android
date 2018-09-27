package dialog.studio.com.dialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = (Button) findViewById(R.id.botaoID);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Título da dialog");

                dialog.setMessage("Mensagem do Dialog");

                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Pressionado Botão Não", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                dialog.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Pressionado Botão Sim", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                dialog.setCancelable(false);
                dialog.setIcon(android.R.drawable.btn_star);
                dialog.create();
                dialog.show();
            }
        });
    }
}

package whatsappandroid.cursoandroid.com.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Permissao;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText codigoPais;
    private EditText ddd;
    private EditText numeroTelefone;
    private Button botaoCadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        nome = (EditText) findViewById(R.id.editTextNomeID);

        codigoPais = (EditText) findViewById(R.id.codigoPaisID);
        SimpleMaskFormatter simpleMaskCodigoPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskCodigoPais = new MaskTextWatcher(codigoPais,simpleMaskCodigoPais);
        codigoPais.addTextChangedListener(maskCodigoPais);

        ddd = (EditText) findViewById(R.id.dddID);
        SimpleMaskFormatter simpleMaskDDD = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskDDD = new MaskTextWatcher(ddd,simpleMaskDDD);
        ddd.addTextChangedListener(maskDDD);

        numeroTelefone = (EditText) findViewById(R.id.numeroTelefoneID);
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(numeroTelefone,simpleMaskTelefone);
        numeroTelefone.addTextChangedListener(maskTelefone);

        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrarID);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codigoPais.getText().toString()+
                        ddd.getText().toString()+
                        numeroTelefone.getText().toString();
                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");

                /*
                //Gerar o token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;

                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "WhatsApp Código de confirmação: "+token;*/

                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario,telefoneSemFormatacao);
                Intent intent = new Intent(LoginActivity.this,ValidadorActivity.class);
                startActivity( new Intent(intent));
                finish();

                /*

                //Envio do SMS
                telefoneSemFormatacao = "5554";

                boolean enviadoSMS = enviaSMS("+"+telefoneSemFormatacao,mensagemEnvio);

                if (enviadoSMS){

                    Intent intent = new Intent(LoginActivity.this,ValidadorActivity.class);
                    startActivity( new Intent(intent));
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente!", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

    }

    private boolean enviaSMS(String telefone, String mensagem){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        for(int resultado: grantResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }

        }

    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas;");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}

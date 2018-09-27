package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class CadastroActivity extends AppCompatActivity {

    private EditText textoUsuario;
    private EditText textoEmail;
    private EditText textoSenha;
    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textoUsuario = (EditText) findViewById(R.id.et_cadastro_usuario_id);
        textoEmail = (EditText) findViewById(R.id.et_cadastro_email_id);
        textoSenha = (EditText) findViewById(R.id.et_cadastro_senha_id);
        botaoCadastrar = (Button) findViewById(R.id.bt_cadastro);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });


    }

    public void abrirLoginUsuario(View view){
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void abriLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void cadastrarUsuario(){
        ParseUser usuario = new ParseUser();

        usuario.setUsername(textoUsuario.getText().toString());
        usuario.setEmail(textoEmail.getText().toString());
        usuario.setPassword(textoSenha.getText().toString());

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if( e == null){
                    Toast.makeText(CadastroActivity.this,"Cadastro efetuado com sucesso!", Toast.LENGTH_LONG).show();
                    abriLoginUsuario();
                }else{
                    ParseErros parseErros = new ParseErros();
                    Toast.makeText(CadastroActivity.this, parseErros.getErro(e.getCode()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class LoginActivity extends AppCompatActivity {

    private EditText textoUsuario;
    private EditText textoSenha;
    private Button botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Verificar se o usuario esta logado
        verificarUsuarioLogado();

        textoUsuario = (EditText) findViewById(R.id.et_login_usuario_id);
        textoSenha = (EditText) findViewById(R.id.et_login_senha_id);
        botaoLogin = (Button) findViewById(R.id.bt_logar);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = textoUsuario.getText().toString();
                String senha = textoSenha.getText().toString();

                verificarLogin(usuario,senha);

            }
        });



    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(intent);
    }

    public void verificarUsuarioLogado(){
        if(ParseUser.getCurrentUser() != null){
            abrirAreaPrincipal();
        }
    }

    private void abrirAreaPrincipal(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void verificarLogin(String usuario, String senha){

        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Toast.makeText(LoginActivity.this,"Login realizado com sucesso.",Toast.LENGTH_LONG).show();
                    abrirAreaPrincipal();

                }else{
                    ParseErros erros = new ParseErros();
                    Toast.makeText(LoginActivity.this,erros.getErro(e.getCode()),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

package com.cursoandroid.autenticacaousuario.autenticacaousuario;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //firebaseAuth.signOut();

        if(firebaseAuth.getCurrentUser() != null){
            Log.i("verificarUsuario", "Usuario esta logado!");
        }else{
            Log.i("verificaUsuario","Usuario não está logado!");
        }

        /*
        //Cadastro
        firebaseAuth.createUserWithEmailAndPassword("aaa@abc.com","123456").addOnCompleteListener(MainActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("createUser","Sucesso ao cadastrar usuário!");
                        }else{
                            Log.i("createUser", "Erro ao cadastrar usuário!");
                        }
                    }
                });
        */

        /*
        //Login
        firebaseAuth.signInWithEmailAndPassword("aaa@abc.com","123456").addOnCompleteListener(
                MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("signInUser","Sucesso ao logar usuário!");
                        }else{
                            Log.i("signInUser", "Erro ao logar usuário!");
                        }
                    }
                }
        );
        */
    }
}

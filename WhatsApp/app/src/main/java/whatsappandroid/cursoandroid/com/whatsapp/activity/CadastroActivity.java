package whatsappandroid.cursoandroid.com.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import whatsappandroid.cursoandroid.com.whatsapp.Config.ConfiguracaoFirebase;
import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;
import whatsappandroid.cursoandroid.com.whatsapp.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = (EditText) findViewById(R.id.editCadastroNomeID);
        email = (EditText) findViewById(R.id.editCadastroEmailID);
        senha = (EditText) findViewById(R.id.editCadastroSenhaID);
        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrarID);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario(
                        nome.getText().toString(),
                        email.getText().toString(),
                        senha.getText().toString()
                        );
                cadastrarUsuario();

            }
        });

    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Sucesso ao cadastrar usuário",Toast.LENGTH_SHORT).show();
                    String identificarUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificarUsuario);
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarDados(identificarUsuario, usuario.getNome());

                    abrirLoginUsuario();
                }else{

                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letra e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O email digitado é inválido, digite um novo email!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Email já cadastrado, digite um novo email!";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,"Erro: "+erroExcecao,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

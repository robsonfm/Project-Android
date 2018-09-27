package whatsappandroid.cursoandroid.com.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import whatsappandroid.cursoandroid.com.whatsapp.R;
import whatsappandroid.cursoandroid.com.whatsapp.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button botaoValidar;
    private Button botaoReenviar;

    private final static String TAG = "PhoneAuth";

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        sendCode();

        codigoValidacao = (EditText) findViewById(R.id.codigoValidacaoID);
        botaoValidar = (Button) findViewById(R.id.botaoValidarID);
        botaoReenviar = (Button) findViewById(R.id.botaoReenviarCodigoID);

        botaoValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = codigoValidacao.getText().toString();

                if (!code.isEmpty() && !phoneVerificationId.isEmpty()){
                    PhoneAuthCredential credential =
                            PhoneAuthProvider.getCredential(phoneVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

        botaoReenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode();
                Toast.makeText(ValidadorActivity.this,"Código reenviado.",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendCode() {

        Preferencias preferencias = new Preferencias(ValidadorActivity.this);
        HashMap<String, String> usuario = preferencias.getDadosUsuario();

        String phoneNumber = "+"+usuario.get("telefone");

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(
                    PhoneAuthCredential credential) {

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d(TAG, "Invalid credential: "
                            + e.getLocalizedMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // SMS quota exceeded
                    Log.d(TAG, "SMS Quota exceeded.");
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                phoneVerificationId = verificationId;
                resendToken = token;

            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(ValidadorActivity.this,"Login Efetuado com Sucesso.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ValidadorActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ValidadorActivity.this,"Erro ao Efetuar Login.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void resendCode() {

        Preferencias preferencias = new Preferencias(ValidadorActivity.this);
        HashMap<String, String> usuario = preferencias.getDadosUsuario();

        //String phoneNumber = "+"+usuario.get("telefone");
        String phoneNumber = "+5531988546662";

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }
}

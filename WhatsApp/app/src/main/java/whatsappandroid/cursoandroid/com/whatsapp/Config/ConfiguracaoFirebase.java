package whatsappandroid.cursoandroid.com.whatsapp.Config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase(){

        if(referenceFirebase == null)
            referenceFirebase = FirebaseDatabase.getInstance().getReference();


        return referenceFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){

        if (autenticacao == null)
            autenticacao = FirebaseAuth.getInstance();

        return autenticacao;
    }

}
package sqlite.studio.com.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            SQLiteDatabase bancoDados = openOrCreateDatabase("aplicativo",MODE_PRIVATE, null);

            //bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR,idade INTEGER)");
            //bancoDados.execSQL("DROP TABLE pessoas");
            //bancoDados.execSQL("INSERT INTO pessoas(nome,idade) VALUES ('Mariana',18)");
            //bancoDados.execSQL( "INSERT INTO pessoas(nome,idade) VALUES ('Tiago',50)");
            //bancoDados.execSQL( "INSERT INTO pessoas(nome,idade) VALUES ('Robson',29)");
            //bancoDados.execSQL( "INSERT INTO pessoas(nome,idade) VALUES ('Marcelo',37)");
            //bancoDados.execSQL( "UPDATE pessoas SET idade = 36 WHERE nome = 'Marcelo'");
            //bancoDados.execSQL( "DELETE FROM pessoas WHERE id > 4");



            Cursor cursor = bancoDados.rawQuery("SELECT * FROM pessoas",null);

            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaNome = cursor.getColumnIndex("nome");
            int indiceColunaIdade = cursor.getColumnIndex("idade");

            cursor.moveToFirst();

            while ( cursor != null){
                Log.i("RESULTADO - id ",cursor.getString(indiceColunaId));
                Log.i("RESULTADO - nome ",cursor.getString(indiceColunaNome));
                Log.i("RESULTADO - idade ",cursor.getString(indiceColunaIdade));
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}

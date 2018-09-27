package todolist.studio.com.todolist;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText textoTarefa;
    private Button botaoAdd;
    private ListView listaTarefas;
    private SQLiteDatabase bancoDados;
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;
    private AlertDialog.Builder caixaDialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            //Recuperar Componentes da tela
            textoTarefa = (EditText) findViewById(R.id.textoTarefaID);
            botaoAdd = (Button) findViewById(R.id.botaoAddID);
            listaTarefas = (ListView) findViewById(R.id.listViewID);

            //Bando de Dados
            bancoDados = openOrCreateDatabase("appTarefas", MODE_PRIVATE, null);

            //Criar a tebela tarefas
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            //Evento de clique
            botaoAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String textoDigitado = textoTarefa.getText().toString();
                    salvarTarefa(textoDigitado);

                }
            });
            listaTarefas.setLongClickable(true);
            listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    removerTarefa(ids.get(position));
                    return true;
                }
            });

            recuperarTarefas();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void salvarTarefa(String texto){
        try{
            if(!texto.equals("")){
                bancoDados.execSQL("INSERT INTO tarefas (tarefa) VALUES('"+texto+"')");
                Toast.makeText(MainActivity.this,"Tarefa salva com sucesso.", Toast.LENGTH_SHORT).show();
                recuperarTarefas();
                textoTarefa.setText("");
            }else{
                Toast.makeText(MainActivity.this,"Nenhuma tarefa digitada.", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void recuperarTarefas(){
        try{
            //Recuperar dados do banco
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC",null);

            //Recuperar os ids das colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");

            //Criar adaptador
            ids = new ArrayList<Integer>();
            itens = new ArrayList<String>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,itens);

            listaTarefas.setAdapter(itensAdaptador);

            cursor.moveToFirst();

            //Listar as terefas
            while (cursor != null){
                Log.i("Resultado - ","id Tarefa: "+cursor.getString(indiceColunaId)+" Tarefa: "+cursor.getString(indiceColunaTarefa));
                itens.add(cursor.getString(indiceColunaTarefa));
                ids.add(Integer.parseInt(cursor.getString(indiceColunaId)));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removerTarefa(final Integer id){

        try{
            //Criar alert Dialog

            caixaDialogo = new AlertDialog.Builder(MainActivity.this);

            //Configurar titulo da Dialog
            caixaDialogo.setTitle("Excluir Tarefa:");

            //Configurar mensagem da Dialog
            caixaDialogo.setMessage("Deseja excluir a tarefa selecionada?");

            //Configurar o botao não
            caixaDialogo.setNegativeButton("Não",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Tarefa mantida.",Toast.LENGTH_SHORT).show();
                        }
                    });
            //Configurar o botao sim
            caixaDialogo.setPositiveButton("Sim",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bancoDados.execSQL("DELETE FROM tarefas WHERE id = "+id);
                            recuperarTarefas();
                            Toast.makeText(getApplicationContext(),"Tareda removida comsucesso.",Toast.LENGTH_SHORT).show();
                        }
                    });
            caixaDialogo.create();
            caixaDialogo.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

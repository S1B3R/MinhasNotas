package com.example.s1b3r.minhasnotas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lista = findViewById(R.id.ListaAnotacoes);
        ArrayList<Anotacao> anotacaoArrayList = new ArrayList<>();

        //SQL AQUI----------------------------------------------------------------------------------
        try {
            SQLiteDatabase notasDB = this.openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
            notasDB.execSQL("CREATE TABLE IF NOT EXISTS anotacoes (id INTEGER NOT NULL PRIMARY KEY, titulo VARCHAR, texto TEXT, data TEXT)");

            Cursor c = notasDB.rawQuery("SELECT * FROM anotacoes", null);

            int eventID = c.getColumnIndex("id");
            int eventTitulo = c.getColumnIndex("titulo");
            int eventTexto = c.getColumnIndex("texto");
            int eventData = c.getColumnIndex("data");

            c.moveToFirst();
            int contador = 0;
            while (c.getCount() > contador){
                //Log.d("Texto",(c.getString(eventTexto)).substring(0,29));//Texto
                Anotacao notaInserirLista = new Anotacao(c.getString(eventData), c.getString(eventTitulo), c.getString(eventTexto), c.getInt(eventID));
                anotacaoArrayList.add(notaInserirLista);
                contador++;
                if(c.getCount() > contador) {
                    c.moveToNext();
                }
            }

            c.close();
            notasDB.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------------------

        AnotacoesArrayAdapter arrayAdapter = new AnotacoesArrayAdapter(this, android.R.layout.list_content, anotacaoArrayList);
        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Anotacao anotacao = new Anotacao()
                Intent intent = new Intent(getApplicationContext(), ActivityCriarEditarTexto.class);
                TextView textViewIDSQL = view.findViewById(R.id.IdSQL);
                intent.putExtra("idSQL", Integer.parseInt(textViewIDSQL.getText().toString()));
                startActivity(intent);
            }
        });

       lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int idSQL = 0;
                //DialogDeletarNota dialogDeletarNota = new DialogDeletarNota();
                //dialogDeletarNota.show(fragmentManagerPRECISA FAZER ESSA BAGAÇA, "");
                TextView textViewIDSQL = view.findViewById(R.id.IdSQL);
                idSQL = Integer.parseInt(textViewIDSQL.getText().toString());
                try{
                    SQLiteDatabase notasDB = openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
                    notasDB.execSQL("DELETE FROM anotacoes WHERE id = "+idSQL+";");
                    onResume();
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //--------------------------------------------------
        try {
            final ListView lista = findViewById(R.id.ListaAnotacoes);
            ArrayList<Anotacao> anotacaoArrayList = new ArrayList<>();

            SQLiteDatabase notasDB = this.openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
            Cursor c = notasDB.rawQuery("SELECT * FROM anotacoes", null);

            int eventID = c.getColumnIndex("id");
            int eventTitulo = c.getColumnIndex("titulo");
            int eventTexto = c.getColumnIndex("texto");
            int eventData = c.getColumnIndex("data");

            c.moveToFirst();
            int contador = 0;
            while (c.getCount() > contador){
                Anotacao notaInserirLista = new Anotacao(c.getString(eventData), c.getString(eventTitulo), c.getString(eventTexto), c.getInt(eventID));
                anotacaoArrayList.add(notaInserirLista);
                contador++;
                if(c.getCount() > contador) {
                    c.moveToNext();
                }
            }

            c.close();
            notasDB.close();

            AnotacoesArrayAdapter arrayAdapter = new AnotacoesArrayAdapter(this, android.R.layout.list_content, anotacaoArrayList);
            lista.setAdapter(arrayAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickNovaNota(View view){
        Intent intent = new Intent(getApplicationContext(), ActivityCriarEditarTexto.class);
        startActivity(intent);
    }
}

//https://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews
//https://developer.android.com/guide/topics/ui/layout/listview.html
//https://developer.android.com/reference/java/util/Date.html
//https://developer.android.com/training/data-storage/room/index.html
//https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
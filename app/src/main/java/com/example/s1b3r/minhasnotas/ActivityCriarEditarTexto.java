package com.example.s1b3r.minhasnotas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class ActivityCriarEditarTexto extends AppCompatActivity {

    public int idSQL = 0;
    public int[] listaIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_editar_texto);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){
            idSQL = bd.getInt("idSQL");
            try {
                SQLiteDatabase notasDB = this.openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
                Cursor c = notasDB.rawQuery("SELECT * FROM anotacoes WHERE id = "+Integer.toString(idSQL), null);
                c.moveToFirst();
                int eventID = c.getColumnIndex("id");
                int eventTitulo = c.getColumnIndex("titulo");
                int eventTexto = c.getColumnIndex("texto");
                int eventData = c.getColumnIndex("data");
                TextView novoTitulo = findViewById(R.id.NovoTitulo);
                novoTitulo.setText(c.getString(eventTitulo));
                TextView novoTexto = findViewById(R.id.NovoTexto);
                novoTexto.setText(c.getString(eventTexto));
                c.close();
                notasDB.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try {
                SQLiteDatabase notasDB = this.openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
                Cursor c = notasDB.rawQuery("SELECT * FROM anotacoes", null);
                listaIDs = new int[c.getCount()];
                int eventID = c.getColumnIndex("id");
                c.moveToFirst();
                int contador = 0;
                while (c.getCount() > contador){
                    listaIDs[contador] = c.getInt(eventID);
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
        }
    }

    public void salvarNota(View view){
        if(idSQL == 0){
            Random rand = new Random();
            int novoId = rand.nextInt(10000)+1;
            boolean unicoIdGerado = false;
            while(!unicoIdGerado){
                if(listaIDs.length > 0) {
                    for (int i = 0; i < listaIDs.length; i++) {
                        if (listaIDs[i] == novoId) {
                            novoId = rand.nextInt(10000) + 1;
                            break;
                        } else if (i + 1 == listaIDs.length) {
                            unicoIdGerado = true;
                        }
                    }
                }else{
                    unicoIdGerado = true;
                }
            }
            try {
                SQLiteDatabase notasDB = this.openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
                TextView titulo = findViewById(R.id.NovoTitulo);
                TextView texto = findViewById(R.id.NovoTexto);
                Calendar dataCriacao = Calendar.getInstance();
                String dataCriacaoString = dataCriacao.get(Calendar.DAY_OF_MONTH) +"/"+(dataCriacao.get(Calendar.MONTH)+1)+"/"+dataCriacao.get(Calendar.YEAR);
                dataCriacaoString += " "+dataCriacao.get(Calendar.HOUR_OF_DAY)+":"+dataCriacao.get(Calendar.MINUTE);
                notasDB.execSQL ("INSERT INTO anotacoes (id, titulo, texto, data) VALUES ('"+novoId+"','"+titulo.getText().toString()+"','"+texto.getText().toString()+"','"+dataCriacaoString+"')");
                notasDB.close();
                idSQL = novoId;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try {
                SQLiteDatabase notasDB = this.openOrCreateDatabase("anotacoes", MODE_PRIVATE, null);
                TextView titulo = findViewById(R.id.NovoTitulo);
                TextView texto = findViewById(R.id.NovoTexto);
                notasDB.execSQL ("UPDATE anotacoes SET titulo = '"+titulo.getText().toString()+"', texto = '"+texto.getText().toString()+"' WHERE id = "+Integer.toString(idSQL)+";");
                notasDB.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        finish();//finish the activity
    }

    public void cancelarNota(View view){
        finish();
    }
}

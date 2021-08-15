package com.alanf.dontbesad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityFrasesBinding;

public class Frases extends Activity {

    private ActivityFrasesBinding binding;
    private ListView listafrases;
    private String[] frases;
    private AppManager apm;
    private Button addb;
    private String fraseinpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFrasesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apm = AppManager.getAppManagerInstance();
        listafrases=findViewById(R.id.listafrases);
        resetList();
        addb=findViewById(R.id.addp);
        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Frases.this);
                builder.setTitle("Nueva frase");
                final EditText input = new EditText(getApplicationContext());
                input.setTextSize(8);
                input.setTextColor(Color.WHITE);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!input.getText().toString().equals("")){
                        apm.addFrase(input.getText().toString());
                        resetList();
                        }else{
                            Toast.makeText(Frases.this,"La frase está vacía",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
    public void resetList(){
        frases=apm.recoverFrases();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, frases);
        listafrases.setAdapter(adapter);
        listafrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value=adapter.getItem(i);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==DialogInterface.BUTTON_POSITIVE){
                            apm.deleteFrase(value);
                            resetList();
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(Frases.this);
                builder.setMessage("Eliminar frase \"" + value +"\"").setPositiveButton("Si", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
}
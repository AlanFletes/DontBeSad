package com.alanf.dontbesad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityMainBinding;

import java.util.logging.Logger;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;
    private ImageButton settings,start,sthap,addimage;
    private SharedPreferences prefs;
    private AppManager apm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apm = AppManager.getAppManagerInstance();
        apm.setSwitching(false);
        settings = findViewById(R.id.settingsbutton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(),Settings.class);
                startActivity(inte);
            }
        });
        start = findViewById(R.id.playbutton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(),AvatarFrame.class);
                startActivity(inte);
            }
        });
        sthap = findViewById(R.id.stopbutton);
        sthap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Detenido",Toast.LENGTH_LONG).show();
            }
        });
        addimage = findViewById(R.id.addimagebutton);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });
        prefs = getSharedPreferences("prefs",0);
        apm.setPrefs(prefs);
        startClocks();
        if (prefs.getBoolean("fr", false)) {
            Toast.makeText(getApplicationContext(), "Abierto anteriormente", Toast.LENGTH_SHORT).show();
            apm.setMuted(prefs.getBoolean("mtd",false));
        } else {
            Toast.makeText(getApplicationContext(), "Primera vez abierto", Toast.LENGTH_SHORT).show();
            prefs.edit().putBoolean("fr",true).apply();
            prefs.edit().putBoolean("mtd",false).apply();
            apm.setMuted(false);
        }
    }
    public void openFile(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent,2);
    }
    public void startClocks(){
        apm.setReloj1Hora(prefs.getInt("h1",0));
        apm.setReloj1Minutos(prefs.getInt("m1",0));
        apm.setReloj1AMPM(prefs.getBoolean("ampm1",false) ? 1:0);
        apm.setReloj2Hora(prefs.getInt("h2",0));
        apm.setReloj2Minutos(prefs.getInt("m2",0));
        apm.setReloj2AMPM(prefs.getBoolean("ampm2",false) ? 1:0);
    }
}
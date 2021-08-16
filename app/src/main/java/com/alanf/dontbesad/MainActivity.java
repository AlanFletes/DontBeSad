package com.alanf.dontbesad;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;
    private ImageButton settings,start,sthap,modifyphrases;
    private SharedPreferences prefs;
    private AppManager apm;
    private boolean lastRun=false;
    private BroadcastReceiver mReceiver;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apm = AppManager.getAppManagerInstance();
        prefs = getSharedPreferences("prefs",0);
        apm.setPrefs(prefs);
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
                if(!apm.getWorking()) {
                    apm.setWorking(true);
                    Toast.makeText(getApplicationContext(), "Comenzarás a recibir frases cada hora dentro del intervalo seleccionado", Toast.LENGTH_SHORT).show();
                    //lanzar alarmas
                    int rha = apm.getHa();
                    int ha = apm.getHa()*60*60*1000;
                    int ma = apm.getMa()*60*1000;
                    int initt = (apm.getReloj1Hora()*60*60*1000)+(apm.getReloj1Minutos()*60*1000);
                    if(ha==0 && ma==0){
                        Toast.makeText(getApplicationContext(),"Configure un intervalo de actividad valido en las configuraciones",Toast.LENGTH_SHORT).show();
                    }else{
                        if(ha==0){
                            Toast.makeText(getApplicationContext(),"Alarma agregada por minutos",Toast.LENGTH_SHORT).show();
                            lastRun=true;
                            alarmManager.set(AlarmManager.RTC_WAKEUP,initt+(ma/2),pendingIntent);
                        }else{
                            long ela =0;
                            Toast.makeText(getApplicationContext(),"Alarma agregada por horas",Toast.LENGTH_SHORT).show();
                            for(int i=0;i<rha;i++){
                                alarmManager.set(AlarmManager.RTC_WAKEUP,initt+(ela+3600000),pendingIntent);
                            }
                        }
                    }
                }
                Intent inte = new Intent(getApplicationContext(),AvatarFrame.class);
                startActivity(inte);
            }
        });
        sthap = findViewById(R.id.stopbutton);
        sthap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apm.setWorking(false);
                Toast.makeText(getApplicationContext(),"Detenido",Toast.LENGTH_LONG).show();
            }
        });
        modifyphrases = findViewById(R.id.addphrasebutton);
        modifyphrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Frases.class));
            }
        });
        startClocks();
        if (prefs.getBoolean("fr", false)) {
            apm.setMuted(prefs.getBoolean("mtd",false));
        } else {
            prefs.edit().putBoolean("fr",true).apply();
            prefs.edit().putBoolean("mtd",false).apply();
            apm.setMuted(false);
        }
        RegisterAlarmBroadcast();
    }
    public void startClocks(){
        apm.setReloj1Hora(prefs.getInt("h1",0));
        apm.setReloj1Minutos(prefs.getInt("m1",0));
        apm.setReloj1AMPM(prefs.getBoolean("ampm1",false) ? 1:0);
        apm.setReloj2Hora(prefs.getInt("h2",0));
        apm.setReloj2Minutos(prefs.getInt("m2",0));
        apm.setReloj2AMPM(prefs.getBoolean("ampm2",false) ? 1:0);
    }
    private void RegisterAlarmBroadcast() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(apm.getTr()==apm.getHa()) lastRun=true;
                apm.setTr(apm.getTr()+1);
                if(lastRun){
                    apm.setTr(0);
                }
                startActivity(new Intent(getApplicationContext(),AvatarFrame.class));
            }
        };
        registerReceiver(mReceiver, new IntentFilter("sample"));
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("sample"), 0);
        alarmManager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
    }
    private void UnregisterAlarmBroadcast() {
        alarmManager.cancel(pendingIntent);
        getBaseContext().unregisterReceiver(mReceiver);
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
package com.alanf.dontbesad;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityClocksetBinding;
import com.kimjio.wear.datetimepicker.app.TimePickerDialog;
import com.kimjio.wear.datetimepicker.widget.TimePicker;

import java.time.LocalTime;
import java.util.Calendar;

public class clockset extends Activity {

    private ActivityClocksetBinding binding;
    private TextView clckin, clckfn;
    private AppManager apm;
    private Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClocksetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clckin = findViewById(R.id.clckin);
        clckfn = findViewById(R.id.clckfin);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/dsdigit.ttf");
        clckfn.setTypeface(font);
        clckin.setTypeface(font);
        apm = AppManager.getAppManagerInstance();
        clckin.setText(String.format("%02d:%02d %s", apm.getReloj1Hora(), apm.getReloj1Minutos(), (apm.getReloj1AMPM() == 1 ? true : false) ? "PM" : "AM"));
        clckfn.setText(String.format("%02d:%02d %s", apm.getReloj2Hora(), apm.getReloj2Minutos(), (apm.getReloj2AMPM() == 1 ? true : false) ? "PM" : "AM"));
        clckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(clockset.this,false).setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        boolean isPM = (hourOfDay >= 12);
                        if (isPM) {
                            apm.setReloj1AMPM(1);
                        } else {
                            apm.setReloj1AMPM(0);
                        }
                        int hour = ((hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12);
                        apm.setReloj1Hora(hour);
                        apm.setReloj1Minutos(minute);
                        SharedPreferences prf = apm.getPrefs();
                        prf.edit().putInt("h1",hour).apply();
                        prf.edit().putInt("m1",minute).apply();
                        prf.edit().putBoolean("ampm1",isPM ? true:false).apply();
                        clckin.setText(String.format("%02d:%02d %s", hour, minute, isPM ? "PM" : "AM"));
                    }
                }).show();
            }
        });
        clckfn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(clockset.this,false).setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        boolean isPM = (hourOfDay >= 12);
                        if (isPM) {
                            apm.setReloj2AMPM(1);
                        } else {
                            apm.setReloj2AMPM(0);
                        }
                        int hour = ((hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12);
                        apm.setReloj2Hora(hour);
                        apm.setReloj2Minutos(minute);
                        SharedPreferences prf = apm.getPrefs();
                        prf.edit().putInt("h2",hour).apply();
                        prf.edit().putInt("m2",minute).apply();
                        prf.edit().putBoolean("ampm2",isPM ? true:false).apply();
                        clckfn.setText(String.format("%02d:%02d %s", hour, minute, isPM ? "PM" : "AM"));
                    }
                }).show();
            }
        });
        aceptar=findViewById(R.id.clckok);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fail = false;
                String reazon="";
                int h1, h2,m1,m2,ampm1,ampm2;
                h1=apm.getReloj1Hora();
                h2=apm.getReloj2Hora();
                m1=apm.getReloj1Minutos();
                m2=apm.getReloj2Minutos();
                ampm1=apm.getReloj1AMPM();
                ampm2=apm.getReloj2AMPM();
                if(ampm1>ampm2){
                    fail=true;
                    reazon = "ampm";
                }else if(h1>h2){
                    fail=true;
                    reazon = "hora";
                }else if(m1>m2 && fail){
                    fail=true;
                    reazon = "minutos";
                }
                if(fail){
                    Toast.makeText(getApplicationContext(),"La hora de inicio es menor a la de fin " + reazon,Toast.LENGTH_SHORT).show();
                }else{
                    int horasactv = 0, minutesactiv = 0, hh1=h1, hh2=h2;
                    if(ampm1==1)hh1=h1+12;
                    if(ampm2==1)hh2=h2+12;
                    minutesactiv=m2-m1;
                    horasactv=hh2-hh1;
                    if(minutesactiv<0) {minutesactiv=60-(minutesactiv*-1); horasactv--;}
                    Toast.makeText(getApplicationContext(),"La app estará activa por " + horasactv + " horas y " + minutesactiv + " minutos.",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
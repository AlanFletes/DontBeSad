package com.alanf.dontbesad;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityAvatarFrameBinding;
import com.google.android.gms.common.internal.Objects;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AvatarFrame extends Activity {

    private ActivityAvatarFrameBinding binding;
    private ImageButton swtch;
    private AppManager apm;
    private ImageView avatarimg;
    private SharedPreferences settings;
    private boolean sw;
    private int place=1,font=1;
    private TextView txtFrase;
    private TextToSpeech speecher;
    private boolean txtrdy=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvatarFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        swtch = findViewById(R.id.swtchbtn);
        sw = true;
        avatarimg = findViewById(R.id.avatarimg);
        apm = AppManager.getAppManagerInstance();
        if (apm.getSwitching()) {
            if (apm.getObjs() == 1) {
                swtch.setImageDrawable(getDrawable(R.drawable.avatarswh));
            }else{
                swtch.setImageDrawable(getDrawable(R.drawable.formatfont));
            }
            swtch.setVisibility(View.VISIBLE);
        } else {
            swtch.setVisibility(View.INVISIBLE);
        }
        txtFrase = findViewById(R.id.txtFrase);
        if(apm.getPrefs().getInt("font",0)!=0){
            setfont(apm.getPrefs().getInt("font",1));
        }
        swtch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(apm.getObjs()==1){
                    if(sw){
                        swtch.setImageDrawable(getDrawable(R.drawable.avatarswl));
                    }else{
                        swtch.setImageDrawable(getDrawable(R.drawable.avatarswh));
                    }
                    if (place >= 3) {
                        place = 1;
                    } else {
                        place++;
                    }
                    setAvatar(place);
                    apm.setAvatar(place);
                    apm.getPrefs().edit().putInt("avatar",place).apply();
                    sw = !sw;
                    //decir hola
                    if(txtrdy){
                        setSpeecherGender(place);
                        if(!apm.getMuted()) {
                            speecher.speak("Hola", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }else{
                    swtch.setImageDrawable(getDrawable(R.drawable.formatfont));
                    if(font>=12){
                        font=1;
                    }else{
                        font++;
                    }
                    setfont(font);
                }
            }
        });
        if(apm.getPrefs().getInt("avatar",0)!=0){
            apm.setAvatar(apm.getPrefs().getInt("avatar",1));
            setAvatar(apm.getPrefs().getInt("avatar",1));
        }
        txtFrase.setText(apm.getRandomFrase());
        speecher = new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener(){
           @Override
           public void onInit(int status){
               Locale spanish = new Locale("es_mx","MX");
               speecher.setLanguage(spanish);
               setSpeecherGender(apm.getAvatar());
               if(!apm.getSwitching()){
                   if(!apm.getMuted()) {
                       speecher.speak(txtFrase.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                   }
               }
               txtrdy=true;
           }
        });
    }
    @Override
    public void onBackPressed(){
        apm.setSwitching(false);
        apm.setObjs(0);
        super.onBackPressed();
    }
    public void setfont(int font){
        Typeface fontt;
        switch (font){
            case 1:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/amtp1.ttf");
                break;
            case 2:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/blockbusta.ttf");
                break;
            case 3:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/compagnonbold.ttf");
                break;
            case 4:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/dsdigit.ttf");
                break;
            case 5:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/evogria.ttf");
                break;
            case 6:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/klgabe.ttf");
                break;
            case 7:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/leorio.ttf");
                break;
            case 8:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/rogerstypewriter.ttf");
                break;
            case 9:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/saniretro.ttf");
                break;
            case 10:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/selectric.ttf");
                break;
            case 11:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/typewriter.ttf");
                break;
            case 12:
                fontt = Typeface.createFromAsset(getAssets(), "fonts/typewriterdry.ttf");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + font);
        }
        apm.setFont(font);
        apm.getPrefs().edit().putInt("font",font).apply();
        txtFrase.setTypeface(fontt);
    }
    public void setAvatar(int avatar){
        switch (avatar){
            case 2:
                avatarimg.setImageDrawable(getDrawable(R.drawable.a));
                break;
            case 3:
                avatarimg.setImageDrawable(getDrawable(R.drawable.h));
                break;
            default:
                avatarimg.setImageDrawable(getDrawable(R.drawable.j));
                break;
        }
    }
    public void setSpeecherGender(int g){
        if(g==2 || g==3){
            Set<String> a=new HashSet<String>();
            a.add("male");
            Voice v=new Voice("es-us-x-sfb#male_3-local",new Locale("es","US"),400,200,true,a);
            speecher.setVoice(v);
            if(g==2){
                speecher.setPitch(0.9f);
            }
            if(g==3){
                speecher.setPitch(0.7f);
                speecher.setSpeechRate(1.2f);
            }
        }else{
            Locale spanish = new Locale("es_mx","MX");
            speecher.setLanguage(spanish);
            speecher.setPitch(0.9f);
        }
    }
}
package com.alanf.dontbesad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivitySettingsBinding;

import java.util.Random;

public class Settings extends Activity {

    private ActivitySettingsBinding binding;
    private ImageButton mute, avatar, font, periodo, animal;
    private AppManager apm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mute = findViewById(R.id.imgbmute);
        avatar = findViewById(R.id.imgbavatar);
        periodo = findViewById(R.id.imgbperiodo);
        apm = AppManager.getAppManagerInstance();
        if(apm.getMuted()){
            mute.setImageDrawable(getDrawable(R.drawable.volumemute));
        }else{
            mute.setImageDrawable(getDrawable(R.drawable.volumehigh));
        }
        periodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),clockset.class));
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(apm.getMuted()){
                    Toast.makeText(getApplicationContext(),"Desmuteado",Toast.LENGTH_SHORT).show();
                    apm.setMuted(false);
                    apm.getPrefs().edit().putBoolean("mtd",false).apply();
                    mute.setImageDrawable(getDrawable(R.drawable.volumehigh));
                }else{
                    Toast.makeText(getApplicationContext(),"Muteado",Toast.LENGTH_SHORT).show();
                    apm.setMuted(true);
                    apm.getPrefs().edit().putBoolean("mtd",true).apply();
                    mute.setImageDrawable(getDrawable(R.drawable.volumemute));
                }
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apm.setSwitching(true);
                apm.setObjs(1);
                startActivity(new Intent(getApplicationContext(),AvatarFrame.class));
            }
        });
        font=findViewById(R.id.imgfont);
        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apm.setSwitching(true);
                apm.setObjs(2);
                startActivity(new Intent(getApplicationContext(),AvatarFrame.class));
            }
        });
        animal = findViewById(R.id.animalrandom);
        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int numero = rand.nextInt(11);
                MediaPlayer mp;
                String animal="";
                switch (numero){
                    case 1:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.burro);
                        animal="Burro";
                    break;
                    case 2:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.cabaio);
                        animal="Caballo";
                    break;
                    case 3:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.delfin);
                        animal="Delfin";
                    break;
                    case 4:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.foca);
                        animal="Foca";
                    break;
                    case 5:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.gaio);
                        animal="Gallo";
                    break;
                    case 6:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.lomito);
                        animal="Perro";
                    break;
                    case 7:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.michi);
                        animal="Gato";
                    break;
                    case 8:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.paharu);
                        animal="Pajaro";
                    break;
                    case 9:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.poios);
                        animal="Gallina";
                    break;
                    case 10:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.vaca);
                        animal="Vaca";
                    break;
                    default:
                        mp=MediaPlayer.create(getApplicationContext(),R.raw.michi);
                        animal="Gato";
                }
                Toast.makeText(getApplicationContext(),animal,Toast.LENGTH_SHORT).show();
                mp.start();
            }
        });
    }
}
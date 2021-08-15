package com.alanf.dontbesad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivitySettingsBinding;

public class Settings extends Activity {

    private ActivitySettingsBinding binding;
    private ImageButton mute, avatar, fondo, font, periodo;
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
                    Toast.makeText(getApplicationContext(),"Muteado",Toast.LENGTH_SHORT).show();
                    apm.setMuted(!apm.getMuted());
                    mute.setImageDrawable(getDrawable(R.drawable.volumemute));
                }else{
                    Toast.makeText(getApplicationContext(),"Desmuteado",Toast.LENGTH_SHORT).show();
                    apm.setMuted(!apm.getMuted());
                    mute.setImageDrawable(getDrawable(R.drawable.volumehigh));
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
    }
}
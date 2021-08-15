package com.alanf.dontbesad;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityAvatarFrameBinding;

public class AvatarFrame extends Activity {

    private ActivityAvatarFrameBinding binding;
    private ImageButton swtch;
    private AppManager apm;
    private SharedPreferences settings;
    private boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvatarFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        swtch = findViewById(R.id.swtchbtn);
        sw=true;
        apm = AppManager.getAppManagerInstance();
        if(apm.getSwitching()){
            if(apm.getObjs()==1){
                swtch.setImageDrawable(getDrawable(R.drawable.avatarswh));
            }
            swtch.setVisibility(View.VISIBLE);
        }else{
            swtch.setVisibility(View.INVISIBLE);
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
                    sw = !sw;
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        apm.setSwitching(false);
        apm.setObjs(0);
        super.onBackPressed();
    }
}
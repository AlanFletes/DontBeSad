package com.alanf.dontbesad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alanf.dontbesad.databinding.ActivityAvatarFrameBinding;

public class AvatarFrame extends Activity {

    private ActivityAvatarFrameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAvatarFrameBinding.inflate(getLayoutInflater());
        Toast.makeText(getApplicationContext(),"Toast!",Toast.LENGTH_SHORT).show();
        setContentView(binding.getRoot());
    }
}
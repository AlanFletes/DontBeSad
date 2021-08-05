package com.alanf.dontbesad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.alanf.dontbesad.databinding.ActivityAvatarFrameBinding;

public class AvatarFrame extends Activity {

    private ActivityAvatarFrameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAvatarFrameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
package com.alanf.dontbesad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.alanf.dontbesad.databinding.ActivitySettingsBinding;

public class Settings extends Activity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
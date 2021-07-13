package com.stdio.instat;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.stdio.instat.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    ActivityPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Uri uri=Uri.parse(getIntent().getStringExtra("videoURL"));
        binding.videoView.setVideoURI(uri);
        binding.videoView.start();
    }
}
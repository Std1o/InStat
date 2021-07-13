package com.stdio.instat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.stdio.instat.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    ActivityPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setDefaultVideoParams();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri=Uri.parse(getIntent().getStringExtra("videoURL"));
        binding.videoView.setVideoURI(uri);
        binding.videoView.start();
    }

    private void setDefaultVideoParams() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.videoView.getLayoutParams();
        params.width = metrics.widthPixels;// max width
        params.height = (int) (params.width * 0.5625);//ratio of height to width is approximately like 720/1280
        binding.videoView.setLayoutParams(params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int newOrientation = newConfig.orientation;

        if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setFullScreenVideoParams();
        } else {
            setDefaultVideoParams();
        }
    }

    private void setFullScreenVideoParams() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) binding.videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        binding.videoView.setLayoutParams(params);
    }
}
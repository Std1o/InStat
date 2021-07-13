package com.stdio.instat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stdio.instat.databinding.ActivityMainBinding;
import com.stdio.instat.interactors.MatchInfoInteractor;
import com.stdio.instat.interactors.VideoUrlsInteractor;
import com.stdio.instat.models.Match;
import com.stdio.instat.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRV();

        MatchInfoInteractor matchInfoInteractor = new MatchInfoInteractor(this);
        VideoUrlsInteractor videoUrlsInteractor = new VideoUrlsInteractor(this);
        presenter = new MainPresenter(matchInfoInteractor, videoUrlsInteractor);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    private void initRV() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        binding.rv.setLayoutManager(llm);
    }

    @Override
    public void showInfo(Match info) {
        binding.tournamentName.setText(info.getTournament().getName_rus());
        binding.date.setText(info.getDate());
        binding.team1.setText(getString(R.string.team_info, info.getTeam1().getName_rus(), info.getTeam1().getScore()));
        binding.team2.setText(getString(R.string.team_info, info.getTeam2().getName_rus(), info.getTeam2().getScore()));
    }

    @Override
    public void showVideos(ArrayList<Video> videos) {
        RVAdapter adapter = new RVAdapter(videos, this);
        binding.rv.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
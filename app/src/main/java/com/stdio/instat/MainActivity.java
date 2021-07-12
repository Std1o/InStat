package com.stdio.instat;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stdio.instat.databinding.ActivityMainBinding;
import com.stdio.instat.models.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MatchInfoInteractor matchInfoInteractor = new MatchInfoInteractor(this);
        presenter = new MainPresenter(matchInfoInteractor);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    public void getVideoList() {
        String url ="https://api.instat.tv/test/video-urls";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Uri uri=Uri.parse(jsonObject.getString("url"));
                        binding.videoView.setVideoURI(uri);
                        binding.videoView.start();
                        System.out.println(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = null;
                try {
                    JSONObject post_dict = new JSONObject();
                    post_dict.put("match_id", 1724836);
                    post_dict.put("sport_id", 1);
                    requestBody = post_dict.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void showInfo(Match info) {
        binding.tournamentName.setText(info.getTournament().getName_rus());
        binding.date.setText(info.getDate());
        binding.team1.setText(getString(R.string.team_info, info.getTeam1().getName_rus(), info.getTeam1().getScore()));
        binding.team2.setText(getString(R.string.team_info, info.getTeam2().getName_rus(), info.getTeam2().getScore()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
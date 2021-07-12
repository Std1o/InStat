package com.stdio.instat;

import com.google.gson.Gson;
import com.stdio.instat.interactors.MatchInfoInteractor;
import com.stdio.instat.interactors.VideoUrlsInteractor;
import com.stdio.instat.models.Match;
import com.stdio.instat.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainPresenter {

    MainView view;
    MatchInfoInteractor matchInfoInteractor;
    VideoUrlsInteractor videoUrlsInteractor;

    public MainPresenter(MatchInfoInteractor matchInfoInteractor, VideoUrlsInteractor videoUrlsInteractor) {
        this.matchInfoInteractor = matchInfoInteractor;
        this.videoUrlsInteractor = videoUrlsInteractor;
    }

    public void attachView(MainView view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        getMatchInfo();
        getVideos();
    }

    public void getMatchInfo() {
        matchInfoInteractor.getMatchInfo(new MatchInfoInteractor.LoadInfoCallback() {
            @Override
            public void onLoad(JSONObject info) {
                Gson gson = new Gson(); // Or use new GsonBuilder().create();
                Match match = gson.fromJson(String.valueOf(info), Match.class);
                System.out.println(match.getTeam2().getName_rus());
                view.showInfo(match);
            }
        });
    }

    public void getVideos() {
        videoUrlsInteractor.getVideos(new VideoUrlsInteractor.LoadVideosCallback() {
            @Override
            public void onLoad(JSONArray jsonArray) {
                Gson gson = new Gson();
                ArrayList<Video> videos = new ArrayList<>();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Video video = gson.fromJson(String.valueOf(jsonArray.get(i)), Video.class);
                        videos.add(video);
                    }
                    view.showVideos(videos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

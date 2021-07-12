package com.stdio.instat;

import android.content.Context;

import com.google.gson.Gson;
import com.stdio.instat.models.Match;

import org.json.JSONObject;

public class MainPresenter {

    MainView view;
    MatchInfoInteractor interactor;

    public MainPresenter(MatchInfoInteractor interactor) {
        this.interactor = interactor;
    }

    public void attachView(MainView view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        getMatchInfo();
    }

    public void getMatchInfo() {
        interactor.getMatchInfo(new MatchInfoInteractor.LoadInfoCallback() {
            @Override
            public void onLoad(JSONObject info) {
                Gson gson = new Gson(); // Or use new GsonBuilder().create();
                Match match = gson.fromJson(String.valueOf(info), Match.class);
                System.out.println(match.getTeam2().getName_rus());
                view.showInfo(match);
            }
        });
    }
}

package com.stdio.instat;

import android.content.Context;

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
                view.showInfo(String.valueOf(info));
            }
        });
    }
}

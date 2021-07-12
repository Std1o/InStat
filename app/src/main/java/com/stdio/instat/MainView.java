package com.stdio.instat;

import com.stdio.instat.models.Match;
import com.stdio.instat.models.Video;

import java.util.ArrayList;

public interface MainView {
    void showInfo(Match info);
    void showVideos(ArrayList<Video> videos);
}

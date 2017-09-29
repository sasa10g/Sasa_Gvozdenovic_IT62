package com.example.sasa10.mljac.aktivnosti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sasa10.mljac.R;
import com.example.sasa10.mljac.klasa.YouTubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends YouTubeBaseActivity {

    private static final String TAG = "YoutubeActivity";

    YouTubePlayerView mYouTubePlayerView;
    Button btnPlay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        Log.d(TAG, "onCreate: String.");
        //btnPlay = (Button) findViewById(R.id.btnPlay);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done initializing.");

                //List<String> videoList = new ArrayList<>();
                //videoList.add("W4hTJybfU7s");
                //videoList.add("W4hTJybfU7s");
                //youTubePlayer.loadVideos(videoList);
                youTubePlayer.loadPlaylist("PLTzMGnJjrsSxns7AaPGwWHBBNUC2HmsB0");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed to initialize.");
            }
        };
           /* btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Initializing YouTube Player.");
                    mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                }

            });


            */

        mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);






    }
}

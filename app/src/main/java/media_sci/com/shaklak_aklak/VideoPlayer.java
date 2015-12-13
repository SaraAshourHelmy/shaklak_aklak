package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Bassem on 11/18/2015.
 */
public class VideoPlayer extends Activity {

    VideoView video_player;
    String video_url = "https://www.youtube.com/watch?v=Z98hXV9GmzY";
    //"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        SetupTools();
    }

    private void SetupTools() {

        video_player = (VideoView) findViewById(R.id.video_player);
        VideoSetting();

        /*
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("video_url")) {
            video_url = bundle.getString("video_url");
            VideoSetting();
        } else {
            // error message
            Toast.makeText(this, "No video url", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void VideoSetting() {
        Uri video_uri = Uri.parse(video_url);
        video_player.setVideoURI(video_uri);
        video_player.start();

        MediaController video_Control = new MediaController(this);
        video_Control.setAnchorView(video_player);
        video_player.setMediaController(video_Control);

    }
}

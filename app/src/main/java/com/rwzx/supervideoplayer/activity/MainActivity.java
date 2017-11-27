package com.rwzx.supervideoplayer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.rwzx.richvideoplayer.media.AndroidMediaController;
import com.rwzx.richvideoplayer.media.IRenderView;
import com.rwzx.richvideoplayer.media.IjkVideoView;
import com.rwzx.supervideoplayer.R;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private IjkVideoView mVideoView;
    private boolean mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        initIjkVideoPlayer();
    }

    private void initIjkVideoPlayer() {

//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_view);
        AndroidMediaController controller = new AndroidMediaController(this, true);
        mVideoView.setMediaController(controller);
        mVideoView.setHudView(tableLayout);
        mVideoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
//        mVideoView.setVideoPath(mVideoPath);
        mVideoView.setVideoURI(Uri.parse("http://118.31.45.21:8180/ShangRaoShi/publish/media/351320555cd6ca42f9cc5bf6cb5644cc.mp4"));
        mVideoView.start();
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    public void launchNiceVideo(View view) {
        startActivity(new Intent(this, NiceVideoActivity.class));
    }
}

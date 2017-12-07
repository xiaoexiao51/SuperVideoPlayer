package com.rwzx.supervideoplayer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        initIjkVideoPlayer();
    }

    private void initIjkVideoPlayer() {

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        AndroidMediaController controller = new AndroidMediaController(this, true);
        mVideoView.setMediaController(controller);
        mVideoView.setHudView((TableLayout) findViewById(R.id.table_view));
        mVideoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
//        mVideoView.setVideoPath(mVideoPath);
        mVideoView.setVideoURI(Uri.parse("http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8"));
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

    // 跳转视频播放
    public void launchNiceVideo(View view) {
        startActivity(new Intent(this, NiceVideoActivity.class));
    }

    // 跳转视频列表
    public void launchVideoList(View view) {
        startActivity(new Intent(this, VideoListActivity.class));
    }
}

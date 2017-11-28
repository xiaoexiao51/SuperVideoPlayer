package com.rwzx.supervideoplayer.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rwzx.supervideoplayer.R;
import com.xiao.nicevideoplayer.Clarity;
import com.xiao.nicevideoplayer.MeasureHelper;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MMM on 2017/11/8.
 */
public class NiceVideoActivity extends AppCompatActivity {

    private NiceVideoPlayer mNiceVideoPlayer;
    private float mVideoPlayerSpeed = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_video_player);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        } else {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        initNiceVideoPlayer();

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroll_view);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= 0) {

                } else if (scrollY > 0 && scrollY <= NiceUtil.dp2px(NiceVideoActivity.this, 300)) {
                    mNiceVideoPlayer.exitTinyWindow();
                } else {
                    mNiceVideoPlayer.enterTinyWindow();
                }
            }
        });
    }

    private void initNiceVideoPlayer() {

        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
//        mNiceVideoPlayer.setUp("rtsp://115.238.93.194:9090/dss/monitor/param?cameraid=1000034%242&substream=1", null);
//        mNiceVideoPlayer.setUp("http://118.31.45.21:8180/ShangRaoShi/publish/media/351320555cd6ca42f9cc5bf6cb5644cc.mp4", null);
//        mNiceVideoPlayer.setUp("http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8", null);
//        mNiceVideoPlayer.setUp("http://192.168.99.107:80/hls1/hls1.m3u8", null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("态度决定一切，细节决定成败，态度决定一切，细节决定成败！");
        controller.isLive(false);
        controller.setClarity(getClarites(), 0);
//        Glide.with(this).load("http://imgsrc.baidu.com/123.jpg").into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
    }

    public void slowSpeed(View view) {
        if (mVideoPlayerSpeed > 0.5f) {
            mVideoPlayerSpeed = mVideoPlayerSpeed - 0.2f;
            mNiceVideoPlayer.setSpeed(mVideoPlayerSpeed);
            ((TextView) findViewById(R.id.tv_speed)).setText("播放速度：" + mVideoPlayerSpeed);
        } else {
            Toast.makeText(this, "不能再慢了", Toast.LENGTH_SHORT).show();
        }
    }

    public void fastSpeed(View view) {
        if (mVideoPlayerSpeed < 1.8f) {
            mVideoPlayerSpeed = mVideoPlayerSpeed + 0.2f;
            mNiceVideoPlayer.setSpeed(mVideoPlayerSpeed);
            ((TextView) findViewById(R.id.tv_speed)).setText("播放速度：" + mVideoPlayerSpeed);
        } else {
            Toast.makeText(this, "不能再快了", Toast.LENGTH_SHORT).show();
        }
    }

    public void aspectRatio(View view) {
//        mNiceVideoPlayer.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
        int aspectRatio = mNiceVideoPlayer.toggleAspectRatio();
        String aspectRatioText = MeasureHelper.getAspectRatioText(this, aspectRatio);
        ((TextView) findViewById(R.id.tv_speed)).setText("缩放模式：" + aspectRatioText);
    }

    public List<Clarity> getClarites() {
        List<Clarity> clarities = new ArrayList<>();
        clarities.add(new Clarity("标清", "270P", "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new Clarity("高清", "480P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new Clarity("超清", "720P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new Clarity("蓝光", "1080P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        return clarities;
    }

    @Override
    protected void onResume() {
        super.onResume();
        NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    public void enterTinyWindow(View view) {
        if (mNiceVideoPlayer.isIdle()) {
            Toast.makeText(this, "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
        } else {
            mNiceVideoPlayer.enterTinyWindow();
        }
    }
}

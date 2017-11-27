# SuperVideoPlayer
基于Ijkplayer打造的视频播放器，支持rtsp、rtmp格式  
  
### 效果图
  
  ![](https://github.com/xiaoexiao51/SuperVideoPlayer/blob/master/screenshot/screenshot01.png)

  ![](https://github.com/xiaoexiao51/SuperVideoPlayer/blob/master/screenshot/screenshot02.png)
    
### 使用步骤  

下面是一些需要注意的地方：
  - 清单文件配置：

1）权限设置：
```
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```  
2）application配置：
```
<!-- 必须初始化 -->
    <activity
            android:name=".activity.NiceVideoActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"/>
```
3）activity代码：
```
    private void initNiceVideoPlayer() {

        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        mNiceVideoPlayer.setUp("http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8", null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("态度决定一切，细节决定成败，态度决定一切，细节决定成败！");
        controller.isLive(true);
//        Glide.with(this).load("http://imgsrc.baidu.com/123.jpg").into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
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
```
  
### 关于我
  
个人邮箱：xiaoexiao51@163.com

[GitHub主页](https://github.com/xiaoexiao51)


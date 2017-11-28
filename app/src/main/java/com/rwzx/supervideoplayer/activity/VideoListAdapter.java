package com.rwzx.supervideoplayer.activity;

import com.bumptech.glide.Glide;
import com.rwzx.supervideoplayer.R;
import com.rwzx.supervideoplayer.base.BaseRecyclerAdapter;
import com.rwzx.supervideoplayer.base.BaseViewHolder;
import com.xiao.nicevideoplayer.Clarity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.List;

/**
 * Created by MMM on 2017/11/27.
 */
public class VideoListAdapter extends BaseRecyclerAdapter<Clarity> {

    public VideoListAdapter(List<Clarity> items) {
        super(items);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.adapter_video_list;
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder holder, int position, Clarity item) {

        NiceVideoPlayer videoPlayer = ((NiceVideoPlayer) holder.getView(R.id.video_view));
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        videoPlayer.setUp(item.videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        controller.setTitle(item.grade);
        controller.isLive(false);
        Glide.with(mContext).load(R.drawable.ic_video_image).into(controller.imageView());
        videoPlayer.setController(controller);
    }
}

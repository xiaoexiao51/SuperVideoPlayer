package com.xiao.nicevideoplayer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by MMM on 2017/9/18.
 * 截图对话框
 */
public class ScreenShotDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private ImageView mScreenShot;

    public ScreenShotDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public ScreenShotDialog(Context context, int themeResId) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_screen_shot, null);
        setContentView(view);
        mScreenShot = ((ImageView) findViewById(R.id.screen_shot));
        findViewById(R.id.txt_confirm).setOnClickListener(this);
        findViewById(R.id.txt_cancel).setOnClickListener(this);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
//        params.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.8
//        params.height = (int) (d.heightPixels * 0.6); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(params);

        ScreenShotDialog.this.setCanceledOnTouchOutside(false);
        ScreenShotDialog.this.setCancelable(true);
    }

    public ScreenShotDialog build(Bitmap screenshotBitmap) {
        if (screenshotBitmap != null) {
            mScreenShot.setImageBitmap(screenshotBitmap);
        }
        return this;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.txt_cancel) {
            if (mOnChooseListener != null) {
                mOnChooseListener.onChooseResult(false);
            }
            ScreenShotDialog.this.dismiss();
        } else if (i == R.id.txt_confirm) {
            if (mOnChooseListener != null) {
                mOnChooseListener.onChooseResult(true);
            }
            ScreenShotDialog.this.dismiss();
        }
    }

    private OnChooseListener mOnChooseListener;

    public interface OnChooseListener {
        void onChooseResult(boolean confirm);
    }

    public ScreenShotDialog setOnChooseListener(OnChooseListener listener) {
        mOnChooseListener = listener;
        return this;
    }
}
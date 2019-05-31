package com.luzuzu.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.bumptech.glide.Glide;
import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.FullScreenAdapter;
import com.luzuzu.main.bean.VideoBean;
import com.luzuzu.main.layoutManager.ViewPagerLayoutManager;
import com.luzuzu.main.listener.OnViewPagerListener;
import com.luzuzu.main.util.IntentKeys;
import com.luzuzu.main.util.SeamlessPlayHelper;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.ui.StandardVideoController;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class FullScreenMainActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private IjkVideoView mIjkVideoView;
    private StandardVideoController fullScreenController;
    private List<VideoBean> mVideoList;
    private FullScreenAdapter fullScreenAdapter;
    private ViewPagerLayoutManager layoutManager;
    private boolean isSeamlessPlay = false;
    private int mCurrentPlayPosition = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_full_screen_main;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        mVideoList=bundle.getParcelableArrayList(IntentKeys.VIDEO_DATA);
        mCurrentPlayPosition = bundle.getInt(IntentKeys.PLAY_POSITION);
        isSeamlessPlay = bundle.getBoolean(IntentKeys.SEAMLESS_PLAY, false);
        mIjkVideoView = SeamlessPlayHelper.getInstance().getIjkVideoView();
        fullScreenController = new StandardVideoController(this);
        mIjkVideoView.setVideoController(fullScreenController);
        mIjkVideoView.setLooping(true);
        fullScreenAdapter = new FullScreenAdapter(this, mVideoList);
        layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fullScreenAdapter);
        recyclerView.scrollToPosition(mCurrentPlayPosition);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                //自动播放第一条
                startPlay(mCurrentPlayPosition, view);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                if (isDestroy(FullScreenMainActivity.this)) {
                    return;
                }
                startPlay(position, view);
                if (isBottom) {
                    EventBus.getDefault().post("needUpdate");
                }
            }
        });
    }

    private void startPlay(int position, View view) {
        FrameLayout playerContainer = view.findViewById(R.id.item_fullscreen_container);
        Glide.with(this)
                .load(mVideoList.get(position).getThumb())
                .into(fullScreenController.getThumb());
        removePlayerFormParent();
        playerContainer.addView(mIjkVideoView);
        if (position == mCurrentPlayPosition && isSeamlessPlay) {
            fullScreenController.setPlayState(mIjkVideoView.getCurrentPlayState());
            fullScreenController.setPlayerState(mIjkVideoView.getCurrentPlayerState());
        } else {
            //必须调用
            mIjkVideoView.addToVideoViewManager();
            mIjkVideoView.release();
            mIjkVideoView.setUrl(mVideoList.get(position).getUrl());
            mIjkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
            mIjkVideoView.start();
        }
        mCurrentPlayPosition = position;
    }

    public static boolean isDestroy(Activity activity) {
        if (activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将播放器从父控件中移除
     */
    private void removePlayerFormParent() {
        ViewParent parent = mIjkVideoView.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mIjkVideoView);
        }
    }

    @OnClick({R.id.main_btn_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_close:
                goBack();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        removePlayerFormParent();
        mIjkVideoView.setVideoController(null);
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putInt(IntentKeys.PLAY_POSITION, mCurrentPlayPosition);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIjkVideoView.resume();
        //KeyboardUtils.toggleSoftInput(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIjkVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isSeamlessPlay) {
            mIjkVideoView.release();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeData(List<VideoBean> mVideoList) {
//        this.mVideoList = mVideoList;
//        fullScreenAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

package com.luzuzu.main.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.library.utils.PxUtils;
import com.luzuzu.library.widget.ShareDialog;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.RecommendRecyclerViewAdapter;
import com.luzuzu.main.bean.VideoBean;
import com.luzuzu.main.itemDecoration.EmptyItemDecoration;
import com.luzuzu.main.layoutManager.ViewPagerLayoutManager;
import com.luzuzu.main.mvp.contract.RecommendContract;
import com.luzuzu.main.mvp.presenter.RecommendPresenter;
import com.luzuzu.main.ui.FullScreenMainActivity;
import com.luzuzu.main.util.IntentKeys;
import com.luzuzu.main.util.SeamlessPlayHelper;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.ui.StandardVideoController;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import static android.app.Activity.RESULT_OK;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by fula on 2019/5/19.
 */

public class RecommendFragment extends BaseFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.main_recommend_recyclerView)
    YCRefreshView ycRefreshView;
    private RecommendRecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private int mCurrentPlayPosition = 0;
    private IjkVideoView mIjkVideoView;
    private StandardVideoController controller;

    @Override
    public int getContentView() {
        return R.layout.main_recommend_fragment;
    }

    @Override
    public void initView(View view) {
        mIjkVideoView = SeamlessPlayHelper.getInstance().getIjkVideoView();
        mIjkVideoView.setVideoController(null);
        mIjkVideoView.setLooping(true);
        mPresenter = new RecommendPresenter(this);
        controller = new StandardVideoController(getContext());
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePlayerFormParent();
                Intent intent = new Intent(getActivity(), FullScreenMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(IntentKeys.SEAMLESS_PLAY, true);
                bundle.putInt(IntentKeys.PLAY_POSITION, mCurrentPlayPosition);
                bundle.putParcelableArrayList(IntentKeys.VIDEO_DATA, (ArrayList<? extends Parcelable>) getVideoList());
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
            }
        });
        //设置刷新颜色
        ycRefreshView.setRefreshingColorResources(R.color.colorAccent);
        adapter = new RecommendRecyclerViewAdapter(getActivity());
        ycRefreshView.setAdapter(adapter);
        linearLayoutManager = new ViewPagerLayoutManager(getActivity(), OrientationHelper.VERTICAL);
        ycRefreshView.setLayoutManager(linearLayoutManager);
        ycRefreshView.addItemDecoration(new EmptyItemDecoration(PxUtils.dp2px(getContext(), 8)));
        adapter.addAll(getVideoList());
        //加载更多
        adapter.setMore(R.layout.view_recycle_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                if (NetworkUtils.isConnected()) {
                    if (adapter.getAllData().size() > 0) {
//                        page++;
//                        presenter.getNews(num,page);
                    } else {
                        adapter.pauseMore();
                    }
                } else {
                    adapter.pauseMore();
                    ToastUtils.showShort("网络不可用");
                }
            }

            @Override
            public void onMoreClick() {

            }
        });

        //设置没有数据
        adapter.setNoMore(R.layout.view_recycle_no_more, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore();
                } else {
                    ToastUtils.showShort("网络不可用");
                }
            }

            @Override
            public void onNoMoreClick() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore();
                } else {
                    ToastUtils.showShort("网络不可用");
                }
            }
        });
        //设置错误
        adapter.setError(R.layout.view_recycle_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });

        adapter.setOnItemChildClickListener(new RecyclerArrayAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.main_tv_share:
                        ShareDialog shareDialog = new ShareDialog(adapter.getContext());
                        shareDialog.show();
                        shareDialog.setOnClickListener(new ShareDialog.OnClickListener() {
                            @Override
                            public void OnClick(View v, int position) {
                                switch (position) {
                                    case 0:
                                        //微博
                                        showShare(SinaWeibo.NAME);
                                        break;
                                    case 1:
                                        //微信
                                        showShare(Wechat.NAME);
                                        break;
                                    case 2:
                                        //朋友圈
                                        showShare(WechatMoments.NAME);
                                        break;
                                }
                            }
                        });
                        break;
                    case R.id.main_tv_comment_num:
                        //onCommentClick(adapter.getItem(position), position);
                        break;
                    case R.id.main_tv_dz_num:
                        doLike();
                        break;
                    case R.id.player_container:
                        Intent intent = new Intent(getActivity(), FullScreenMainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(IntentKeys.SEAMLESS_PLAY, true);
                        bundle.putInt(IntentKeys.PLAY_POSITION, mCurrentPlayPosition);
                        bundle.putParcelableArrayList(IntentKeys.VIDEO_DATA, (ArrayList<? extends Parcelable>) getVideoList());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 200);
                        break;
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            mCurrentPlayPosition = bundle.getInt(IntentKeys.PLAY_POSITION, 0);
            ycRefreshView.scrollToPosition(mCurrentPlayPosition);
            LogUtils.e("当前位置是：" + mCurrentPlayPosition);
            ycRefreshView.getRecyclerView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                    .OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //所以完成了需求后需要移除OnGlobalLayoutListener
                    ycRefreshView.getRecyclerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    View view = linearLayoutManager.findViewByPosition(mCurrentPlayPosition);
                    CardView playerContainer = view.findViewById(R.id.player_container);
                    controller.setPlayState(mIjkVideoView.getCurrentPlayState());
                    controller.setPlayerState(mIjkVideoView.getCurrentPlayerState());
                    mIjkVideoView.setVideoController(controller);
                    playerContainer.addView(mIjkVideoView);
                }
            });
        }
    }

    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        //启动分享
        oks.show(getActivity());
    }

    @Override
    public void initListener() {
        ycRefreshView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem, visibleCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case SCROLL_STATE_IDLE: //滚动停止
                        autoPlayVideo(recyclerView);
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem;//记录可视区域item个数
            }

            private void autoPlayVideo(RecyclerView view) {
                //循环遍历可视区域videoview,如果完全可见就开始播放
                for (int i = 0; i < visibleCount; i++) {
                    View itemView = view.getChildAt(i);
                    if (itemView == null) continue;
                    CardView playerContainer = itemView.findViewById(R.id.player_container);
                    Rect rect = new Rect();
                    playerContainer.getLocalVisibleRect(rect);
                    int videoHeight = playerContainer.getHeight();
                    if (rect.top == 0 && rect.bottom == videoHeight) {
                        int position = (int) itemView.getTag();
                        mIjkVideoView.release();
                        VideoBean videoBean = adapter.getItem(position);
                        mIjkVideoView.setUrl(videoBean.getUrl());
                        removePlayerFormParent();
                        playerContainer.addView(mIjkVideoView);
                        //必须调用
                        mIjkVideoView.addToVideoViewManager();
                        mIjkVideoView.setVideoController(controller);
                        mIjkVideoView.start();
                        mCurrentPlayPosition = position;
                        break;
                    }
                }
            }
        });
        ycRefreshView.post(new Runnable() {
            @Override
            public void run() {
                //自动播放第一个
                VideoBean videoBean = adapter.getItem(0);
                mIjkVideoView.setUrl(videoBean.getUrl());
                mIjkVideoView.setVideoController(controller);
                mIjkVideoView.start();
                mCurrentPlayPosition = 0;
                View view = ycRefreshView.getChildAt(0);
                CardView playerContainer = view.findViewById(R.id.player_container);
                playerContainer.addView(mIjkVideoView);
            }
        });
        //设置刷新 listener
        ycRefreshView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新操作
                if (NetworkUtils.isConnected()) {
                    //page++;
                    //presenter.getNews(num,page);
                } else {
                    ycRefreshView.setRefreshing(false);
                    ToastUtils.showShort("网络不可用");
                }
            }
        });
    }

    /**
     * 将播放器从父控件中移除
     */
    private void removePlayerFormParent() {
        ViewParent parent = mIjkVideoView.getParent();
        if (parent instanceof CardView) {
            ((CardView) parent).removeView(mIjkVideoView);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setErrorView() {
        ycRefreshView.setErrorView(R.layout.view_custom_data_error);
        ycRefreshView.showError();
        LinearLayout ll_error_view = ycRefreshView.findViewById(R.id.ll_error_view);
        ll_error_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    @Override
    public void setNetworkErrorView() {
        ycRefreshView.setErrorView(R.layout.view_custom_network_error);
        ycRefreshView.showError();
        LinearLayout ll_set_network = ycRefreshView.findViewById(R.id.ll_set_network);
        ll_set_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtils.isConnected()) {
                    initData();
                } else {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void setView(List<VideoBean> videoList) {
        if (adapter != null) {
            adapter.clear();
        } else {
            adapter = new RecommendRecyclerViewAdapter(getActivity());
        }
        adapter.addAll(getVideoList());
        adapter.notifyDataSetChanged();
        ycRefreshView.showRecycler();
    }

    @Override
    public void setEmptyView() {
        ycRefreshView.setEmptyView(R.layout.view_custom_empty_data);
        ycRefreshView.showEmpty();
    }

    @Override
    public void setViewMore(List<VideoBean> videoList) {
        adapter.addAll(videoList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopMore() {
        adapter.stopMore();
    }


    public static List<VideoBean> getVideoList() {
        List<VideoBean> videoList = new ArrayList<>();
        videoList.add(new VideoBean("七舅脑爷| 脑爷烧脑三重奏，谁动了我的蛋糕",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0", null));

        videoList.add(new VideoBean("七舅脑爷| 你会不会在爱情中迷失了自我，从而遗忘你正拥有的美好？",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/02/2018-02-09_23-573150677-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2018/02/29/056bf3fabc41a1c1257ea7f69b5ee787.mp4", "https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4"));

        videoList.add(new VideoBean("七舅脑爷| 别因为你的患得患失，就怀疑爱情的重量",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/02/2018-02-23_57-2208169443-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2018/02/29/db48634c0e7e3eaa4583aa48b4b3180f.mp4", null));

        videoList.add(new VideoBean("七舅脑爷| 女员工遭老板调戏，被同事陷害，双面夹击路在何方？",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/12/2017-12-08_39-829276539-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2017/12/29/fc821f9a8673d2994f9c2cb9b27233a3.mp4", null));

        videoList.add(new VideoBean("七舅脑爷| 夺人女友，帮人作弊，不正经的学霸比校霸都可怕。",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/01/2018-01-05_49-2212350172-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2018/01/29/bc95044a9c40ec2d8bdf4ac9f8c50f44.mp4", null));

        videoList.add(new VideoBean("七舅脑爷| 男子被困秘密房间上演绝命游戏, 背后凶手竟是他?",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/11/2017-11-10_10-320769792-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2017/11/29/15f22f48466180232ca50ec25b0711a7.mp4", null));

        videoList.add(new VideoBean("七舅脑爷| 男人玩心机，真真假假，我究竟变成了谁？",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/11/2017-11-03_37-744135043-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2017/11/29/7c21c43ba0817742ff0224e9bcdf12b6.mp4", null));
        //EventBus.getDefault().post(videoList);
        return videoList;
    }


    //评论
    private void onCommentClick(VideoBean bean, final int position) {
        mCurrentPlayPosition = position;
        //移除Controller
        mIjkVideoView.setVideoController(null);
        Intent intent = new Intent(getContext(), FullScreenMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKeys.PLAY_POSITION, position);
        bundle.putBoolean(IntentKeys.SEAMLESS_PLAY, true);
        intent.putExtras(bundle);
        startActivityForResult(intent, 200);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void needUpdate() {
        //getVideoList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIjkVideoView.resume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mIjkVideoView.pause();
        //VideoViewManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIjkVideoView.release();
    }

    //点赞
    private void doLike() {
        ToastUtils.showShort("点赞了");
    }

}

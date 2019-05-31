package com.luzuzu.main.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.library.utils.PxUtils;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.FollowAdapter;
import com.luzuzu.main.adapter.VideoAdapter;
import com.luzuzu.main.bean.FollowBean;
import com.luzuzu.main.bean.VideoBean;
import com.luzuzu.main.itemDecoration.GridDividerItemDecoration;
import com.luzuzu.main.ui.FullScreenMainActivity;
import com.luzuzu.main.ui.UpManActivity;
import com.luzuzu.main.util.IntentKeys;
import com.luzuzu.main.util.SeamlessPlayHelper;
import com.luzuzu.main.widget.controller.RotateInFullscreenController;
import com.luzuzu.videoplayer.player.IjkVideoView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by fula on 2019/5/19.
 */

public class MainFollowFragment extends BaseFragment {

    @BindView(R.id.main_up_man_recycler)
    RecyclerView upManRecycler;
    @BindView(R.id.main_video_recycler)
    RecyclerView videoRecycler;
    private FollowAdapter followAdapter;
    private VideoAdapter videoAdapter;

    @Override
    public int getContentView() {
        return R.layout.main_follow_fragment;
    }

    @Override
    public void initView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        upManRecycler.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        videoRecycler.addItemDecoration(new GridDividerItemDecoration(PxUtils.dp2px(getContext(),5)));
        videoRecycler.setLayoutManager(gridLayoutManager);
        followAdapter = new FollowAdapter(getContext());
        upManRecycler.setAdapter(followAdapter);
        videoAdapter = new VideoAdapter(getContext());
        videoRecycler.setAdapter(videoAdapter);
    }

    @Override
    public void initListener() {
        videoAdapter.setOnItemChildClickListener(new RecyclerArrayAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                Intent intent = new Intent(getActivity(), FullScreenMainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt(IntentKeys.PLAY_POSITION,position);
                bundle.putParcelableArrayList(IntentKeys.VIDEO_DATA, (ArrayList<? extends Parcelable>) getVideoList());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        followAdapter.setOnItemChildClickListener(new RecyclerArrayAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                if(view.getId()==R.id.iv_follow_avatar)
                {
                    startActivity(UpManActivity.class);
                }
            }
        });
    }

    @Override
    public void initData() {
        List<FollowBean> list = new ArrayList<>();
        list.add(new FollowBean("张三", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("张三", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("张三", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("张三", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("张三", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        followAdapter.addAll(list);
        videoAdapter.addAll(getVideoList());
    }

    public List<VideoBean> getVideoList() {
        List<VideoBean> videoList = new ArrayList<>();
        videoList.add(new VideoBean("七舅脑爷| 脑爷烧脑三重奏，谁动了我的蛋糕",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2018/03/29/8b5ecf95be5c5928b6a89f589f5e3637.mp4", null));

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
        videoList.add(new VideoBean("七舅脑爷| 脑爷烧脑三重奏，谁动了我的蛋糕",
                "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg",
                "http://cdnxdc.tanzi88.com/XDC/dvideo/2018/03/29/8b5ecf95be5c5928b6a89f589f5e3637.mp4", null));

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
}

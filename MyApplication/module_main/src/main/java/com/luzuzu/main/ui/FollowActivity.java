package com.luzuzu.main.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;

import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.MainViewPagerAdapter;
import com.luzuzu.main.model.MainPageItem;
import com.luzuzu.main.ui.fragment.FollowFragment;
import com.luzuzu.main.ui.fragment.FollowerFragment;
import com.luzuzu.main.ui.fragment.MainFollowFragment;
import com.luzuzu.main.ui.fragment.RecommendFragment;
import com.luzuzu.main.widget.custemerTablayout.SlidingScaleTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class FollowActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    SlidingScaleTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private FragmentManager fragmentManager;

    @Override
    public int getContentView() {
        return R.layout.activity_follow;
    }

    @Override
    public void initView() {
        ArrayList<MainPageItem> aList = new ArrayList<>();
        View followerView = getLayoutInflater().inflate(R.layout.main_recommend_view, null, false);
        View followView = getLayoutInflater().inflate(R.layout.main_follow_view, null, false);
        aList.add(new MainPageItem(followerView, "粉丝"));
        aList.add(new MainPageItem(followView, "关注"));
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(aList);
        mViewPager.setAdapter(mainViewPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction recommendTransaction = fragmentManager.beginTransaction();
                recommendTransaction.add(R.id.main_fragment_recommend_container, new FollowerFragment());
                recommendTransaction.commit();

                FragmentTransaction followTransaction = fragmentManager.beginTransaction();
                followTransaction.add(R.id.main_fragment_follow_container, new FollowFragment());
                followTransaction.commit();
                mViewPager.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this);
            }
        });
    }


    @OnClick({R.id.main_follow_back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_follow_back_btn:
                finish();
                break;

        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

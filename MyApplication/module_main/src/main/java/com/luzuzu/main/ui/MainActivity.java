package com.luzuzu.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.MainViewPagerAdapter;
import com.luzuzu.main.model.MainPageItem;
import com.luzuzu.main.mvp.contract.MainContract;
import com.luzuzu.main.mvp.presenter.MainPresenter;
import com.luzuzu.main.ui.fragment.MainFollowFragment;
import com.luzuzu.main.ui.fragment.RecommendFragment;
import com.luzuzu.main.util.SeamlessPlayHelper;
import com.luzuzu.main.widget.custemerTablayout.SlidingScaleTabLayout;
import com.luzuzu.videoplayer.player.IjkVideoView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity<MainPresenter> implements EasyPermissions.PermissionCallbacks, MainContract.View {

    @BindView(R.id.tab_layout)
    SlidingScaleTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private RecommendFragment recommendFragment;
    private MainFollowFragment followFragment;
    private FragmentManager fragmentManager;
    private long time;
    private IjkVideoView mIjkVideoView;
    private MainContract.Presenter presenter = new MainPresenter(this);

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void initView() {
        initPermissions();
        ArrayList<MainPageItem> aList = new ArrayList<>();
        View recommendView = getLayoutInflater().inflate(R.layout.main_recommend_view, null, false);
        View followView = getLayoutInflater().inflate(R.layout.main_follow_view, null, false);
        aList.add(new MainPageItem(recommendView, "推荐"));
        aList.add(new MainPageItem(followView, "关注"));
        mViewPager.setOffscreenPageLimit(1);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(aList);
        mViewPager.setAdapter(mainViewPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        mIjkVideoView = SeamlessPlayHelper.getInstance().getIjkVideoView();
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fragmentManager = getSupportFragmentManager();
                recommendFragment = new RecommendFragment();
                FragmentTransaction recommendTransaction = fragmentManager.beginTransaction();
                recommendTransaction.add(R.id.main_fragment_recommend_container, recommendFragment);
                recommendTransaction.commit();

                FragmentTransaction followTransaction = fragmentManager.beginTransaction();
                followFragment = new MainFollowFragment();
                followTransaction.add(R.id.main_fragment_follow_container, followFragment);
                followTransaction.commit();
                mViewPager.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mIjkVideoView.resume();
                } else {
                    mIjkVideoView.pause();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化权限
     */
    private void initPermissions() {
        //权限
        presenter.locationPermissionsTask();
    }

    /**
     * 将结果转发到EasyPermissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions, grantResults, MainActivity.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        if (EasyPermissions.somePermissionPermanentlyDenied(MainActivity.this, perms)) {
//            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(MainActivity.this);
//            builder.setTitle("允许权限")
//                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
//                    .setPositiveButton("去设置")
//                    .setNegativeButton("取消")
//                    .setRequestCode(124)
//                    .build()
//                    .show();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    /**
     * 是当某个按键被按下是触发。所以也有人在点击返回键的时候去执行该方法来做判断
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //双击返回桌面
            if ((System.currentTimeMillis() - time > 1000)) {
                ToastUtils.showShort("再按一次返回桌面");
                time = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

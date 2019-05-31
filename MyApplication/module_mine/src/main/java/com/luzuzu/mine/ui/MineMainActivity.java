package com.luzuzu.mine.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.mine.R;
import com.luzuzu.mine.ui.user.fragment.UserFragment;

public class MineMainActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_mine_main;
    }

    @Override
    public void initView() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mine_user_container, new UserFragment());
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void initListener() {

    }


    @Override
    public void initData() {

    }
}

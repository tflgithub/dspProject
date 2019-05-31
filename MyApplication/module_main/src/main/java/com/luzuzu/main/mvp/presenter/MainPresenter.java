package com.luzuzu.main.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import com.luzuzu.main.R;
import com.luzuzu.main.mvp.contract.MainContract;

import io.reactivex.disposables.CompositeDisposable;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by fula on 2019/3/11.
 */

public class MainPresenter implements MainContract.Presenter {

    private CompositeDisposable mCompositeDisposable;
    private Activity activity;

    public MainPresenter(MainContract.View view) {
        this.activity = (Activity) view;
    }

    @Override
    public void subscribe() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (activity != null) {
            activity = null;
        }
    }

    @Override
    public void locationPermissionsTask() {
        startPermissionsTask();
    }

    private static final int RC_ALL_PERMS = 124;
    private static final String[] ALL_PERMS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @AfterPermissionGranted(RC_ALL_PERMS)
    private void startPermissionsTask() {
        //检查是否获取该权限
        if (hasPermissions()) {
            //具备权限 直接进行操作
            //Toast.makeText(this, "Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(activity,
                    activity.getResources().getString(R.string.easy_permissions),
                    RC_ALL_PERMS, ALL_PERMS);
        }
    }

    /**
     * 判断是否添加了权限
     *
     * @return true
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(activity, ALL_PERMS);
    }

}

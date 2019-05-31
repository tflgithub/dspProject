package com.luzuzu.main.ui.fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.FollowAdapter;
import com.luzuzu.main.bean.FollowBean;

import org.yczbj.ycrefreshviewlib.YCRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FollowerFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    YCRefreshView refreshView;
    private FollowAdapter followAdapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_follower;
    }

    @Override
    public void initView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        refreshView.setLayoutManager(linearLayoutManager);
        followAdapter = new FollowAdapter(getContext());
        refreshView.setAdapter(followAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        List<FollowBean> list = new ArrayList<>();
        list.add(new FollowBean("乐滋滋", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("乐滋滋", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("乐滋滋", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("乐滋滋", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        list.add(new FollowBean("乐滋滋", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2018/03/2018-03-30_10-1782811316-750x420.jpg", 100, false));
        followAdapter.addAll(list);
    }
}

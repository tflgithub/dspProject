package com.luzuzu.mine.ui.user;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.library.widget.MultipleItemView;
import com.luzuzu.mine.R;
import butterknife.BindView;

public class UserEditActivity extends BaseActivity {


    @BindView(R.id.mine_toolbar)
    Toolbar toolbar;

    @BindView(R.id.mine_miv_nick_name)
    MultipleItemView nickName;

    @BindView(R.id.mine_miv_sex)
    MultipleItemView sex;

    @BindView(R.id.mine_miv_signature)
    MultipleItemView signature;

    @Override
    public int getContentView() {
        return R.layout.activity_user_edit;
    }

    @Override
    public void initView() {
        toolbar.setTitle("修改信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void initListener() {
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickName.getEditText().setSelection(nickName.getRightTex().length());
            }
        });

        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signature.getEditText().setSelection(signature.getRightTex().length());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_edit_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_finish:
                break;
            case android.R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {

    }
}

package com.luzuzu.mine.ui.account.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.luzuzu.library.arouter.ARouterConstant;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.library.widget.ObserverButton;
import com.luzuzu.mine.R;
import com.luzuzu.mine.R2;
import com.luzuzu.mine.ui.account.mvp.presenter.LoginPresenter;
import com.luzuzu.mine.widget.PhoneCode;
import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterConstant.FRAGMENT_BIND_MOBILE)
public class BindMobileFragment extends BaseFragment<LoginPresenter> {


    @BindView(R2.id.ob_login)
    ObserverButton obLogin;

    @BindView(R2.id.mine_mobile_edit)
    EditText edtMobile;

    @BindView(R2.id.mine_login_phone_code)
    PhoneCode phoneCode;

    @BindView(R2.id.mine_update_mobile)
    Button clearBtn;

    @Override
    public int getContentView() {
        return R.layout.fragment_bind_mobile;
    }

    @Override
    public void initView(View view) {
        obLogin.observer(edtMobile);
        inputMobile();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private void inputMobile() {
        clearBtn.setText("清空");
        edtMobile.setText("");
        phoneCode.et_code.setText("");
        edtMobile.setFocusable(true);
        edtMobile.setFocusableInTouchMode(true);
        edtMobile.requestFocus();
        edtMobile.setVisibility(View.VISIBLE);
        phoneCode.setVisibility(View.GONE);
    }

    //获取验证码
    private void getCode() {
        clearBtn.setText("修改");
        phoneCode.setVisibility(View.VISIBLE);
        phoneCode.et_code.setFocusable(true);
        phoneCode.et_code.setFocusableInTouchMode(true);
        phoneCode.et_code.requestFocus();
    }

    @OnClick({R2.id.ob_login, R2.id.mine_update_mobile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ob_login:
                getCode();
                break;
            case R.id.mine_update_mobile:
                inputMobile();
                break;
        }
    }
}

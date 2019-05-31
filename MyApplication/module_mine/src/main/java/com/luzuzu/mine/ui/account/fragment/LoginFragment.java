package com.luzuzu.mine.ui.account.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luzuzu.library.arouter.ARouterConstant;
import com.luzuzu.library.arouter.ARouterUtils;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.library.bean.Event;
import com.luzuzu.library.constant.EventAction;
import com.luzuzu.library.widget.ObserverButton;
import com.luzuzu.mine.R;
import com.luzuzu.mine.R2;
import com.luzuzu.mine.ui.account.LoginActivity;
import com.luzuzu.mine.ui.account.mvp.contract.LoginContract;
import com.luzuzu.mine.ui.account.mvp.presenter.LoginPresenter;
import com.luzuzu.mine.widget.PhoneCode;
import com.luzuzu.mine.wxapi.WXEntryActivity;
import com.luzuzu.mine.wxapi.WxLogin;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterConstant.FRAGMENT_LOGIN)
public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.ob_login)
    ObserverButton obLogin;
    @BindView(R2.id.mine_mobile_edit)
    EditText edtMobile;

    @BindView(R2.id.mine_other_login)
    LinearLayout otherLogin;

    @BindView(R2.id.mine_login_phone_code)
    PhoneCode phoneCode;

    @BindView(R2.id.mine_update_mobile)
    Button clearBtn;

    private LoginContract.Presenter presenter = new LoginPresenter(this);

    @Override
    public int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(View view) {
        presenter.bindActivity((LoginActivity) getActivity());
        inputMobile();
        obLogin.observer(edtMobile);
    }

    @Override
    public void initListener() {
        phoneCode.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSuccess(String code) {
                //去登录
                presenter.login();
            }
        });
        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    clearBtn.setVisibility(View.VISIBLE);
                } else {
                    clearBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R2.id.ob_login, R2.id.mine_update_mobile, R2.id.mine_wx_login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ob_login:
                getCode();
                break;
            case R.id.mine_update_mobile:
                inputMobile();
                break;
            case R.id.mine_wx_login_btn:
                WxLogin.longWx();
                break;
        }
    }


    private void inputMobile() {
        clearBtn.setText("清空");
        edtMobile.setText("");
        phoneCode.et_code.setText("");
        edtMobile.setFocusable(true);
        edtMobile.setFocusableInTouchMode(true);
        edtMobile.requestFocus();
        edtMobile.setVisibility(View.VISIBLE);
        otherLogin.setVisibility(View.VISIBLE);
        phoneCode.setVisibility(View.GONE);
    }

    //获取验证码
    private void getCode() {
        clearBtn.setText("修改");
        phoneCode.setVisibility(View.VISIBLE);
        otherLogin.setVisibility(View.GONE);
        phoneCode.et_code.setFocusable(true);
        phoneCode.et_code.setFocusableInTouchMode(true);
        phoneCode.et_code.requestFocus();
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (event.getAction() == EventAction.EVENT_LOGIN_WX_LOGIN_SUCESS) {
            //去登录服务器，判断有没有绑定手机号码，如果没有就先绑定手机
            BindMobileFragment fragment = (BindMobileFragment) ARouterUtils.getFragment(ARouterConstant.FRAGMENT_BIND_MOBILE);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mine_login_container, fragment);
            transaction.commit();
        }
    }

    @Override
    protected boolean regEvent() {
        return true;
    }

    @Override
    public void loginSuccess() {
        EventBus.getDefault().post(new Event(EventAction.EVENT_LOGIN_SUCCESS));
        (getActivity()).finish();
    }
}

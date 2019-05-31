package com.luzuzu.mine.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.library.bean.Event;
import com.luzuzu.library.constant.EventAction;
import com.luzuzu.library.utils.httpUtils.HttpUtils;
import com.luzuzu.mine.R;
import com.luzuzu.mine.bean.WxUserInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private IWXAPI mWeixinAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_wxentry);
        mWeixinAPI = WXAPIFactory.createWXAPI(this, WxData.WEIXIN_APP_ID, true);
        mWeixinAPI.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWeixinAPI.handleIntent(intent, this);//必须调用此句话
    }


    @Override
    public void onReq(BaseReq baseReq) {
        finish();
        Log.e("-----", "onReq: " + baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("-----", "errStr: " + baseResp.errStr);
        Log.e("-----", "openId: " + baseResp.openId);
        Log.e("-----", "transaction: " + baseResp.transaction);
        Log.e("-----", "errCode: " + baseResp.errCode);
        Log.e("-----", "getType: " + baseResp.getType());
        Log.e("-----", "checkArgs: " + baseResp.checkArgs());

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.showShort("登录失败");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) baseResp).code;
                        getAccess_token(code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        Log.e("--------", "code: " + code);
                        break;
                }
                break;
        }
    }


    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WxData.WEIXIN_APP_ID
                + "&secret="
                + WxData.APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("-----", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String openid = jsonObject.getString("openid").toString().trim();
                    String access_token = jsonObject.getString("access_token").toString().trim();
                    getUserMesg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;

        HttpUtils.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String nickname = jsonObject.getString("nickname");
                    int sex = Integer.parseInt(jsonObject.get("sex").toString());
                    String headimgurl = jsonObject.getString("headimgurl");
                    String openid = jsonObject.getString("openid");
                    Log.e("---", "用户基本信息:");
                    Log.e("---", "nickname:" + nickname);
                    Log.e("---", "sex:       " + sex);
                    Log.e("---", "headimgurl:" + headimgurl);
                    WxUserInfo wxUserInfo = new WxUserInfo(nickname, headimgurl, sex);
                    EventBus.getDefault().post(new Event(EventAction.EVENT_LOGIN_WX_LOGIN_SUCESS,wxUserInfo));
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShort("登陆错误,请重新再试");
                }
                finish();
            }
        });
    }
}

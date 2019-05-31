package com.luzuzu.mine.bean;

/**
 * Created by fula on 2019/5/29.
 */

public class WxUserInfo {

    public String nickName;

    public WxUserInfo(String nickName, String headImgUrl, int sex) {
        this.nickName = nickName;

        this.headImgUrl = headImgUrl;

        this.sex = sex;
    }

    public String headImgUrl;

    public int sex;
}

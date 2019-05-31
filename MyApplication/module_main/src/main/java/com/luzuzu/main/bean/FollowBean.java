package com.luzuzu.main.bean;

/**
 * Created by fula on 2019/5/21.
 */

public class FollowBean {

    public String uid;

    public String nickName;

    public String avatar;

    public int followNum;

    public boolean isFollow;

    public FollowBean(String nickName, String avatar, int followNum, boolean isFollow) {
        this.nickName = nickName;
        this.avatar = avatar;
        this.followNum = followNum;
        this.isFollow = isFollow;
    }
}

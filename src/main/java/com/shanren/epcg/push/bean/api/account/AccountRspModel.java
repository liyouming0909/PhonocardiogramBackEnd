package com.shanren.epcg.push.bean.api.account;

import com.shanren.epcg.push.bean.card.UserCard;
import com.shanren.epcg.push.bean.db.User;
import lombok.Data;


/**
 * 账户部分返回的Model
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@Data
public class AccountRspModel {
    // 用户基本信息
    private UserCard user;

    // 当前登录的账号
    private String account;

    // 当前登录成功后获取的Token,
    // 可以通过Token获取用户的所有信息
    private String token;

    // 标示是否已经绑定到了设备PushId
    private String isBind;

    public AccountRspModel(User user) {
        // 默认无绑定
        this(user, false);
    }

    public AccountRspModel(User user, boolean isBind) {
        this.user = new UserCard(user);
        this.account = user.getName();
        this.token = user.getToken();
        this.isBind = String.valueOf(isBind);
    }
}

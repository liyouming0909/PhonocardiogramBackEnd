package com.shanren.epcg.push.bean.api.account;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@Data
public class RegisterModel {

    private String account;

    private String password;

    private String pushId;

    // 校验
    public static boolean check(RegisterModel model) {
        return model != null
                && StringUtils.isNotEmpty(model.account)
                && StringUtils.isNotEmpty(model.password);
    }
}

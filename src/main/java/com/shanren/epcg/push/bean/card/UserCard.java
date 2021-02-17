package com.shanren.epcg.push.bean.card;

import com.shanren.epcg.push.bean.db.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@Data
public class UserCard {

    private String id;

    private String name;

    private String phone;

    private String area;

    private String unitName;

    private String department;

    private String deviceAdmin;

    private String portrait;

    // 用户信息最后的更新时间

    private LocalDateTime modifyAt;


    public UserCard(final User user) {

        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.area = user.getArea();
        this.unitName = user.getUnitName();
        this.department = user.getDepartment();
        this.deviceAdmin = user.getDeviceAdmin();
        this.portrait = user.getPortrait();
        this.modifyAt = user.getUpdateAt();

    }
}

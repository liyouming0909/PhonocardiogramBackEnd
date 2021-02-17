package com.shanren.epcg.push.bean.api.user;

import com.shanren.epcg.push.bean.db.User;
import lombok.Data;
import org.apache.commons.lang.StringUtils;


/**
 * 用户更新信息，完善信息的Model
 *
 * @author
 * @version 1.0.0
 */
@Data
public class UpdateInfoModel {

    private String name;

    private String phone;

    private String area;

    private String unitName;

    private String department;

    private String deviceAdmin;

    private String portrait;


    /**
     * 把当前的信息，填充到用户Model中
     * 方便UserModel进行写入
     *
     * @param user User Model
     * @return User Model
     */
    public User updateToUser(User user) {
        if (StringUtils.isNotEmpty(name))
            user.setName(name);

        if (StringUtils.isNotEmpty(portrait))
            user.setPortrait(portrait);

        if (StringUtils.isNotEmpty(phone))
            user.setPhone(phone);

        if (StringUtils.isNotEmpty(area))
            user.setArea(area);

        if (StringUtils.isNotEmpty(unitName))
            user.setUnitName(unitName);

        if (StringUtils.isNotEmpty(department))
            user.setDepartment(department);

        if (StringUtils.isNotEmpty(deviceAdmin))
            user.setDeviceAdmin(deviceAdmin);

        return user;
    }

    public static boolean check(UpdateInfoModel model) {
        // Model 不允许为null，
        // 并且只需要具有一个及其以上的参数即可
        return model != null
                && (StringUtils.isNotEmpty(model.name)||
                StringUtils.isNotEmpty(model.portrait) ||
                StringUtils.isNotEmpty(model.phone) ||
                StringUtils.isNotEmpty(model.area) ||
                StringUtils.isNotEmpty(model.deviceAdmin)||
                StringUtils.isNotEmpty(model.unitName) ||
                StringUtils.isNotEmpty(model.department));
    }

}

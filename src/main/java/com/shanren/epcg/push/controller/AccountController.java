package com.shanren.epcg.push.controller;

import com.shanren.epcg.push.bean.api.account.AccountRspModel;
import com.shanren.epcg.push.bean.api.account.LoginModel;
import com.shanren.epcg.push.bean.api.account.RegisterModel;
import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author qiujuer
 */
// 127.0.0.1/api/account/...
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    // 登录
    // 指定请求与返回的相应体为JSON
    @PostMapping("/account/login")
    public ResponseModel<AccountRspModel> login(@RequestBody LoginModel model) {
        if (!LoginModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }


        User user = accountService.login(model.getAccount(), model.getPassword());
        if (user != null) {
            // 如果有携带PushId
            if (StringUtils.isNotEmpty(model.getPushId())) {
                return accountService.bindPushId(user, model.getPushId());
            }

            // 返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            // 登录失败
            return ResponseModel.buildLoginError();
        }
    }


    // 注册
    // 指定请求与返回的相应体为JSON
    @PostMapping("/account/register")
    public ResponseModel<AccountRspModel> register(@RequestBody RegisterModel model) {
        if (!RegisterModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        User user = accountService.findByName(model.getAccount().trim());
        if (user != null) {
            // 已有用户名
            return ResponseModel.buildHaveNameError();
        }

        // 开始注册逻辑
        user = accountService.register(model.getAccount(),
                model.getPassword());

        if (user != null) {

            // 如果有携带PushId
            if (StringUtils.isNotEmpty(model.getPushId())) {
                return accountService.bindPushId(user,model.getPushId());
            }

            // 返回当前的账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            // 注册异常
            return ResponseModel.buildRegisterError();
        }
    }


    // 绑定设备Id
    // 指定请求与返回的相应体为JSON
    // 从请求头中获取token字段
    // pushId从url地址中获取
    @PostMapping("/account/bind/{pushId}")
    public ResponseModel<AccountRspModel> bind(@RequestHeader("token") String token,
                                               @PathVariable("pushId") String pushId) {
        if (StringUtils.isEmpty(token) ||
                StringUtils.isEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        // 拿到自己的个人信息
        User user = accountService.findByToken(token);
        //User self = getSelf();
        return accountService.bindPushId(user,pushId);
    }

}

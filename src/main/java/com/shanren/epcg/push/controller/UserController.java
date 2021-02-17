package com.shanren.epcg.push.controller;


import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.api.user.UpdateInfoModel;
import com.shanren.epcg.push.bean.card.UserCard;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 用户信息处理的Service
 *
 */
@RestController
public class UserController {

    @Autowired
    private AccountService accountService;

    // 用户信息修改接口
    // 返回自己的个人信息
    @PutMapping("/user")
    public ResponseModel<UserCard> update(@RequestBody UpdateInfoModel model,@RequestAttribute("user") User self) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        // 更新用户信息
        self = model.updateToUser(self);
        //保存到数据库
        accountService.update(self);
        // 构架自己的用户信息
        UserCard card = new UserCard(self);
        // 返回
        return ResponseModel.buildOk(card);
    }


    //获取某人的信息
    @GetMapping("/user/{id}")
    public ResponseModel<UserCard> getUser(@PathVariable("id") String id,@RequestAttribute("user") User self){

        if(StringUtils.isEmpty(id)){
            //返回参数异常
            return ResponseModel.buildParameterError();
        }

        if (String.valueOf(self.getId()).equalsIgnoreCase(id)){
            //返回自己，不必查询数据库
            return ResponseModel.buildOk(new UserCard(self));
        }

        User user = accountService.findById(id);
        if (user == null){
            //没找到，返回没找到用户
            return ResponseModel.buildNotFoundUserError(null);
        }

        return ResponseModel.buildOk(new UserCard(user));
    }
}

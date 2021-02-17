package com.shanren.epcg.push.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanren.epcg.push.bean.api.account.AccountRspModel;
import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.db.Patient;
import com.shanren.epcg.push.bean.db.Record;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.mapper.PatientMapper;
import com.shanren.epcg.push.mapper.RecordMapper;
import com.shanren.epcg.push.mapper.UserMapper;
import com.shanren.epcg.push.utils.TextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private PatientMapper patientMapper;

    // 通过Token字段查询用户信息
    // 只能自己使用，查询的信息是个人信息，非他人信息
    public User findByToken(String token) {
        return findByOneField("token",token);
    }

    // 通过Name找到User
    public User findByName(String name) {
        return findByOneField("name",name);
    }

    public User findByOneField(String fieldName, String fieldValue){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq(fieldName, fieldValue);
        return userMapper.selectOne(queryWrapper);
    }

    // 通过Id找到User
    public User findById(String id) {
        //通过Id查询，更方便
        return userMapper.selectById(id);
    }

    /**
     * 更新用户信息到数据库
     *
     * @param user User
     * @return User
     */
    public int update(User user) {
        return userMapper.updateById(user);
    }


    /**
     * 给当前的账户绑定PushId
     *
     * @param user   自己的User
     * @param pushId 自己设备的PushId
     * @return User
     */
    public ResponseModel bindPushId(User user, String pushId) {
        if (StringUtils.isEmpty(pushId))
            return null;

        // 第一步，查询是否有其他账户绑定了这个设备
        // 取消绑定，避免推送混乱
        // 查询的列表不能包括自己
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("pushId", pushId);
        List<User> userList = userMapper.selectList(queryWrapper);

        for (User u : userList) {
            // 更新为null
            u.setPushId(null);
            userMapper.updateById(u);
        }

        if (!StringUtils.equalsIgnoreCase(pushId,user.getPushId())) {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (StringUtils.isEmpty(user.getPushId())) {
                // TODO 推送一个退出消息
            }

            // 更新新的设备Id
            user.setPushId(pushId);
            userMapper.updateById(user);
        }
        // 返回当前的账户, 并且已经绑定了
        AccountRspModel rspModel = new AccountRspModel(user, true);
        return ResponseModel.buildOk(rspModel);
    }

    /**
     * 使用账户和密码进行登录
     */
    public User login(String account, String password) {
        final String accountStr = account.trim();
        // 把原文进行同样的处理，然后才能匹配
        final String encodePassword = encodePassword(password);

        // 寻找
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",accountStr);
        queryWrapper.eq("password",encodePassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            // 对User进行登录操作，更新Token
            user = login(user);
        }
        return user;
    }


    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库中的User信息
     *
     * @param account  账户
     * @param password 密码
     * @return User
     */
    public User register(String account, String password) {
        // 去除账户中的首位空格
        account = account.trim();
        // 处理密码
        password = encodePassword(password);

        User user = createUser(account, password);
        if (user != null) {
            user = login(user);
        }
        return user;
    }


    /**
     * 注册部分的新建用户逻辑
     *
     * @param account  设备名
     * @param password 加密后的密码
     * @return 返回一个用户
     */
    private  User createUser(String account, String password) {
        User user = new User();
        // 账户就是设备名
        user.setName(account);
        user.setPassword(password);

        // 数据库存储
        userMapper.insert(user);
        return user;
    }


    /**
     * 把一个User进行登录操作
     * 本质上是对Token进行操作
     *
     * @param user User
     * @return User
     */
    private User login(User user) {
        // 使用一个随机的UUID值充当Token
        String newToken = UUID.randomUUID().toString();
        // 进行一次Base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);

        userMapper.updateById(user);
        return user;
    }


    /**
     * 对密码进行加密操作
     *
     * @param password 原文
     * @return 密文
     */
    private static String encodePassword(String password) {
        // 密码去除首位空格
        password = password.trim();
        // 进行MD5非对称加密，加盐会更安全，盐也需要存储
        password = TextUtil.getMD5(password);
        // 再进行一次对称的Base64加密，当然可以采取加盐的方案
        return TextUtil.encodeBase64(password);
    }

    /**
     * 获取我的患者库列表
     *
     * @param self User
     * @return List<User>
     */
    public List<Patient> contacts(User self) {
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", self.getId());
        List<Record> recordList = recordMapper.selectList(wrapper);
        return patientMapper.selectBatchIds(recordList.stream().map(record -> record.getPatientId()).collect(Collectors.toList()));

    }
}

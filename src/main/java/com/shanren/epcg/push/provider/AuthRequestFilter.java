package com.shanren.epcg.push.provider;

import com.alibaba.fastjson.JSONObject;
import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用于所有的请求的接口的过滤和拦截ø
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@Component
public class AuthRequestFilter implements Filter {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AccountService accountService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 检查是否是登录注册接口
        String relationPath = httpServletRequest.getRequestURI();
        if (relationPath.startsWith("/api/account/login")
                || relationPath.startsWith("/api/account/register")) {
            // 直接走正常逻辑，不做拦截
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        // 从Headers中去找到第一个token节点
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isNotEmpty(token)) {
            // 查询自己的信息
            User self = accountService.findByToken(token);
            if (self != null) {
                // 给当前请求添加一个上下文
                httpServletRequest.setAttribute("user", self);
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        // 直接返回一个账户需要登录的Model
        ResponseModel model = ResponseModel.buildAccountError();
        // 构建一个返回
        // 拦截，停止一个请求的继续下发，调用该方法后之间返回请求
        // 不会走到Service中去
        servletResponse.getWriter().print(JSONObject.toJSON(model));
    }
}

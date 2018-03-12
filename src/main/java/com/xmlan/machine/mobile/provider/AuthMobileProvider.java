package com.xmlan.machine.mobile.provider;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.system.service.LoginService;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 手机端 登录接口
 *
 * Package: com.xmlan.machine.mobile.provider
 *
 * @author ayakurayuki
 * @date 2018/1/12-13:47
 */
@Controller
@RequestMapping("${mobilePath}")
public class AuthMobileProvider extends BaseController {

    private final LoginService loginService;

    @Autowired
    public AuthMobileProvider(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 登录
     * <p>
     * URL: /mob/auth
     * <p>
     * Method: Get/Post
     *
     * @param authname String 登录名
     * @param password String 密码
     * @return 登录结果，包括用户ID和token
     */
    @RequestMapping(value = "/auth", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap auth(String authname, String password) {
        User user = loginService.loginForMobile(authname, password);
        // 采用客户端记录的方式，回传用户ID和Token
        if (user != null) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("id", user.getId());
            map.put("token", TokenUtils.getToken(authname, password));
            return map;
        } else {
            return Maps.newHashMap();
        }
    }

}

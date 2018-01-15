package com.xmlan.machine.common.util

import com.google.common.collect.Maps
import com.xmlan.machine.module.system.service.LoginService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Created by ayakurayuki on 2018/1/15-10:47. <br/>
 * Package: com.xmlan.machine.common.util <br/>
 */
final class TokenUtils {

    private Logger logger = LogManager.getLogger(TokenUtils.class)
    private static String authname = 'authname'
    private static String password = 'password'

    private static LoginService loginService = SpringContextUtils.getBean(LoginService.class)

    /**
     * 获取Token
     * @param auth 登录名
     * @param pass 密码
     * @return Token字串
     */
    static String getToken(String auth, String pass) {
        def info = Maps.newHashMap()
        info[authname] = auth
        info[password] = pass
        return EncodeUtils.encodeBase64(JsonUtils.toJsonString(info))
    }

    static boolean validateToken(String token) {
        def decode = EncodeUtils.decodeBase64String(token)
        def info = JsonUtils.fromJsonString(decode, HashMap.class) as HashMap<String, String>
        def user = loginService.loginForMobile(info[authname], info[password])
        return user != null
    }

}

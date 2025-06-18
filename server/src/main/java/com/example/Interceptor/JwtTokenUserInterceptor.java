package com.example.Interceptor;

import com.example.constant.JwtClaimsConstant;
import com.example.context.BaseContext;
import com.example.properties.JwtProperties;
import com.example.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            //校验成功后解析JWT令牌里面的ID,转化为long类型的
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.LOGIN_ID).toString());
            //然后借助ThreadLocal来存放这个empId到这个线程的空间中去,后续Service层中对Employee对象赋值时再取出来
            BaseContext.setCurrentId(id);

            log.info("当前用户id：{}", id);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            // 获取异常的简单描述
            String message = ex.getMessage();
            System.out.println("异常信息: " + message);
            ex.printStackTrace();
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
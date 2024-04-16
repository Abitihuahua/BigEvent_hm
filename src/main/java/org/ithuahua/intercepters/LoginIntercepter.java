package org.ithuahua.intercepters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ithuahua.utils.JwtUtil;
import org.ithuahua.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginIntercepter implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储到TreadLocal内部
            ThreadLocalUtil.set(claims);
            //拦截器中返回true就放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        清理ThreadLocalUtil内的数据，防止信息泄露
        ThreadLocalUtil.remove();
    }
}
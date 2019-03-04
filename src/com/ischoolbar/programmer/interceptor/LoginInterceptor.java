package com.ischoolbar.programmer.interceptor;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        Object user = request.getSession().getAttribute("user");
        if(user == null){
//            String header = request.getHeader("X-Requested-With");
//            if("XMLHttpRequest".equals(header)){
//                Map<String,String> map = new HashMap<>();
//                map.put("type","error");
//                map.put("msg","登录会话超时或未登录，请重新登录");
//                response.getWriter().write(JSONObject.fromObject(map).toString());
//                return false;
//            }
            response.sendRedirect(request.getServletContext().getContextPath()+"/system/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}

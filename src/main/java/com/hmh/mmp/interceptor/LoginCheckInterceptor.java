package com.hmh.mmp.interceptor;

import com.hmh.mmp.common.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();

        System.out.println("requestURI = " + requestURI);

        HttpSession session = request.getSession();

        if (session.getAttribute(SessionConst.LOGIN_EMAIL) == null) {
            response.sendRedirect("/member/login?redirectURL=" + requestURI);

            return false;
        } else {

            return true;
        }
    }
}

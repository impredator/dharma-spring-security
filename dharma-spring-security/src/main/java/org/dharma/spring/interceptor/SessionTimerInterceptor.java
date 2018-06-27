package org.dharma.spring.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimerInterceptor extends HandlerInterceptorAdapter {

    private static final long MAX_INACTIVE_SESSION_TIME = 1 * 10000;

    @Autowired
    private HttpSession session;

    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        System.out.println("Pre handle method - check handling start time");
        long startTime = System.currentTimeMillis();
        request.setAttribute("executionTime", startTime);
        if (UserInterceptor.isUserLogged()) {
            session = request.getSession();
            System.out.println("Time since last request in this session: " + (System.currentTimeMillis() - request.getSession().getLastAccessedTime()) + " ms");
            if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
                System.out.println("Logging out, due to inactive session");
                SecurityContextHolder.clearContext();
                request.logout();
                response.sendRedirect("/dharma_logout");
            }
        }
        return true;
    }

    /**
     * Executed before after handler is executed
     **/
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView model) throws Exception {
        System.out.println("Post handle method - check execution time of handling");
        long startTime = (Long) request.getAttribute("executionTime");
        System.out.println("Execution time for handling the request was: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}

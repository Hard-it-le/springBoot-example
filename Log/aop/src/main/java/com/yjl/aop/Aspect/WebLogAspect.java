package com.yjl.aop.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/11
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {
    /**
     * 切入点 *com.yjl.aop 包下所有的类
     * *该类中的所有方法
     * (..) 所有的方法参数
     */
    @Pointcut("execution(public * com.yjl.aop.*.*(..))")
    public void webLog() {
    }

    /**
     * 前置通知 请求方法之前做拦截
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            log.info("name:{},value:{}", name, request.getParameter(name));
        }
    }

    /**
     * 目标方法请求之后 打印（响应）信息
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
}

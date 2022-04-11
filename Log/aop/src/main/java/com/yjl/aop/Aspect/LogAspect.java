package com.yjl.aop.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/11
 * @Aspect:这个注解表示将当前类视为一个切面类
 * @Component：表示将当前类交由Spring管理。
 * @Pointcut:切点表达式,定义我们的匹配规则，上边我们使用@Pointcut("@annotation(com.web.springbootaoplog.config.Log)")表示匹配带有我们自定义注解的方法。
 * @Around:环绕通知，可以在目标方法执行前后执行一些操作，以及目标方法抛出异常时执行的操作。
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(LogAspect.class);


    /**
     * 表示匹配带有自定义注解的方法
     */
    @Pointcut("@annotation(com.yjl.aop.config.Log)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        long beginTime = System.currentTimeMillis();

        try {
            log.info("我在目标方法之前执行！");
            result = point.proceed();
            long endTime = System.currentTimeMillis();
            insertLog(point, endTime - beginTime);
        } catch (Throwable e) {
            // TODO Auto-generated catch block
        }
        return result;
    }

    private void insertLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
    }
}

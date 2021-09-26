package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sun.rmi.runtime.Log;

/**统一异常处理类：处理控制器发生的异常
 * 一个异常处理类，可以有多个异常处理方法
 * @author snake
 * @create 2021-09-24 9:13
 */
//@RestControllerAdvice:SpringMVC提供：表示当前类是处理器通知通知用的
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    //编写处理异常的方法
    //@ExceptionHandler表示下面方法专门处理传入参数异常
    @ExceptionHandler
    public String handlerServiceException(ServiceException e){
        log.error("业务异常",e);
        return e.getMessage();
    }

    @ExceptionHandler
    public String handlerException(Exception e){
        log.error("其他异常",e);
        return e.getMessage();
    }
}

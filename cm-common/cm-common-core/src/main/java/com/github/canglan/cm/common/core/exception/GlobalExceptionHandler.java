package com.github.canglan.cm.common.core.exception;

import com.github.canglan.cm.common.core.model.ExceptionResult;
import com.github.canglan.cm.common.core.model.Result;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

  @Resource
  MessageSource messageSource;

  /**
   * 请求缺少参数异常
   */
  @ExceptionHandler({org.springframework.web.bind.MissingServletRequestParameterException.class})
  @ResponseBody
  public Result processRequestParameterException(HttpServletRequest request,
      HttpServletResponse response,
      MissingServletRequestParameterException e) {
    log.error("捕获 MissingServletRequestParameterException ", e);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json;charset=UTF-8");
    Result result = new Result();
    result.setCode(HttpStatus.BAD_REQUEST.value());
    result.setMessage(
        messageSource.getMessage(HttpStatus.BAD_REQUEST.value() + "",
            null, LocaleContextHolder.getLocale()) + e.getParameterName());
    return result;
  }

  /**
   * 程序异常
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ExceptionResult<String> processDefaultException(HttpServletResponse response, Exception e) {
    log.error("捕获 Exception", e);
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType("application/json;charset=UTF-8");
    ExceptionResult<String> result = new ExceptionResult<>();

    result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    result.setMessage(
        messageSource.getMessage(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", null, LocaleContextHolder.getLocale())
    );
    return result;
  }
  //java -javaagent:D:/toolkit/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar -Dskywalking_config=D:/toolkit/apache-skywalking-apm-bin-es7/agent/config/agent-copy.config -jar cm-gateway-1.0-SNAPSHOT.jar

  /**
   * 外部请求发生异常
   */
  @ExceptionHandler(ApiException.class)
  @ResponseBody
  public Result processApiException(HttpServletResponse response,
      ApiException e) {
    log.error("捕获 ApiException", e);
    Result result = new Result();
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType("application/json;charset=UTF-8");
    result.setCode(e.getCode());
    String message = messageSource.getMessage(e.getCode() + "", null, LocaleContextHolder.getLocale());
    result.setMessage(message);
    return result;
  }

  /**
   * 内部微服务异常统一处理方法
   */
  @ExceptionHandler(InternalApiException.class)
  @ResponseBody
  public Result processMicroServiceException(HttpServletResponse response,
      InternalApiException e) {
    log.error("捕获 InternalApiException", e);
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType("application/json;charset=UTF-8");
    Result result = new Result();
    result.setCode(e.getCode());
    result.setMessage(e.getMessage());
    return result;
  }
}

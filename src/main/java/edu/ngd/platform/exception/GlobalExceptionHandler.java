package edu.ngd.platform.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理所有异常
     * @param request HTTP请求
     * @param e 异常对象
     * @param model 模型对象
     * @return 错误页面视图
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e, Model model) {
        ModelAndView mv = new ModelAndView("error/error");
        
        // 设置错误信息
        mv.addObject("errorMessage", e.getMessage());
        mv.addObject("errorUrl", request.getRequestURI());
        mv.addObject("errorStack", e.getStackTrace());
        
        // 记录日志
        System.err.println("发生异常：" + e.getMessage());
        e.printStackTrace();
        
        return mv;
    }
    
    /**
     * 处理业务异常
     * @param request HTTP请求
     * @param e 业务异常对象
     * @param model 模型对象
     * @return 错误页面视图
     */
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(HttpServletRequest request, BusinessException e, Model model) {
        ModelAndView mv = new ModelAndView("error/error");
        
        // 设置错误信息
        mv.addObject("errorMessage", e.getMessage());
        mv.addObject("errorUrl", request.getRequestURI());
        
        // 记录日志
        System.err.println("业务异常：" + e.getMessage());
        
        return mv;
    }
}
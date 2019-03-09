package com.eastreach.pest.error;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常助手类
 **/
//@ControllerAdvice
@RestControllerAdvice
public class ExceptionHandler {

    public static final String ERROR_VIEW = "error";


//    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
//    public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
//        e.printStackTrace();
//
//        return e.getMessage();
////        ModelAndView view = new ModelAndView();
////        view.addObject("exception", e);
////        view.addObject("url", request.getRequestURL());
////        view.setViewName(ERROR_VIEW);
////        return view;
//    }
}

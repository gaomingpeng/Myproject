package com.gmp.common.annotation;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;

    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrentUser currentUser = methodParameter.getParameterAnnotation(CurrentUser.class);
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
           return  request.getSession().getAttribute(currentUser.value());
      //  return nativeWebRequest.getAttribute(currentUser.value(),NativeWebRequest.SCOPE_REQUEST);
    }
}

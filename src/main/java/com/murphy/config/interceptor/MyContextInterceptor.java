package com.murphy.config.interceptor;

import com.murphy.common.ErrorCode;
import com.murphy.constants.UserConstant;
import com.murphy.exception.BusinessException;
import com.murphy.model.entity.sys.SysUser;
import com.murphy.model.vo.LoginUserVO;
import com.murphy.service.sys.SysUserService;
import com.murphy.utils.JWTUtils;
import com.murphy.utils.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(UserConstant.HEADER_NAME);
        Mycontext context = MyContextHolder.getContext();
        SysUser sysUser = null;
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if(StringUtils.isEmpty(token)){
            throw new BusinessException(ErrorCode.EMPTY_TOKEN);
        }

        LoginUserVO user = JWTUtils.verify(token);
        if(user.getValid() && user != null){
            SysUserService sysUserService = SpringContextUtils.getBean(SysUserService.class);
            sysUser = sysUserService.selectByPrimaryKey(user.getId());
            context.setSysUser(sysUser);
        }else {
            throw new BusinessException(ErrorCode.ERROR_TOKEN);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MyContextHolder.clearContext();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

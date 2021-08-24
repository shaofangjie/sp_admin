package com.sp.admin.interceptor;

import cn.hutool.core.util.StrUtil;
import com.sp.admin.annotation.authentication.IgnorePermissionCheck;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    ConfigurableApplicationContext context;

    /*
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.info("执行到了preHandle方法");
        logger.info(handler.toString());
        //获取控制器的名字
        logger.info(((HandlerMethod)handler).getBean().getClass().getName());
        //获取方法名
        logger.info(((HandlerMethod)handler).getMethod().getName());
        boolean isLogin = null != request.getSession().getAttribute("isLogin");
        if (isLogin && checkPermission((HandlerMethod)handler)) {
            logger.info("合格不需要拦截，放行");
            return true;
        } else {
            response.sendRedirect(request.getContextPath()+"/login");//拦截后跳转的方法
            logger.info("已成功拦截并转发跳转");
            return false;
        }
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("执行了postHandle方法");
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        logger.info("执行到了afterCompletion方法");
    }

    private boolean checkPermission(HandlerMethod handler) {
        String checkAction = "";
        String classsName = handler.getBean().getClass().getName();
        String methodName = handler.getMethod().getName();
        logger.info(classsName + methodName);
        boolean ignorePermissionCheck = false;
        try {
            Method[] methods = Class.forName(classsName).getMethods();
            //判断指定方法是否有 IgnorePermissionCheck SpecifiedPermission 注解
            for (Method method :methods) {
                IgnorePermissionCheck ignorePermissionCheckAnnotation = method.getAnnotation(IgnorePermissionCheck.class);
                SpecifiedPermission specifiedPermissionAnnotation = method.getAnnotation(SpecifiedPermission.class);
                if (methodName.equals(method.getName()) && null != ignorePermissionCheckAnnotation) {
                    ignorePermissionCheck = true;
                    break;
                }
                if (methodName.equals(method.getName()) && null != specifiedPermissionAnnotation) {
                    checkAction = specifiedPermissionAnnotation.value();
                    break;
                }
            }

            if (ignorePermissionCheck) {
                return true;
            } else {
                return !StrUtil.hasBlank(checkAction) && hasPermission(checkAction);
            }

        } catch (Exception e) {
            logger.info("%s权限校验异常%s", classsName + methodName, e);
            return false;
        }
    }

    private boolean hasPermission(String checkAction) {
        //TODO数据库获取权限信息

        return true;
    }

}
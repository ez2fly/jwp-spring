package next.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import next.LoginUser;
import next.controller.UserController;
import next.controller.UserSessionUtils;



public class LoginUserResv implements HandlerMethodArgumentResolver {
	 private static final Logger log = LoggerFactory.getLogger(LoginUserResv.class);
	 
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		
		return webRequest.getAttribute(UserSessionUtils.USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
	}
	
}

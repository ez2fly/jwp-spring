package next.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

class UppercaseAdvice implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		// TODO Auto-generated method stub
		String ret = (String) arg0.proceed();
		return ret.toUpperCase();
	}
}